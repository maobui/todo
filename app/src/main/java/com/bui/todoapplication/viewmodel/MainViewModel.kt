package com.bui.todoapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bui.todoapplication.repository.ProductRepository
import com.bui.todoapplication.local.TodoRoomDatabase
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import com.bui.todoapplication.remote.ApiBuilder
import com.bui.todoapplication.remote.TodoApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val application: Application) : ViewModel() {

    private val database by lazy { TodoRoomDatabase.getDatabase(application) }
    private val service = ApiBuilder.buildService(TodoApi::class.java)
    private val productRepository by lazy { ProductRepository(database.productDao(), service) }
    private val _loading = MutableLiveData<Boolean>()

    val loading: MutableLiveData<Boolean>
        get() = _loading
    val productsSaved: LiveData<List<Product>> = productRepository.productsSaved.asLiveData()

    fun save(product: Product) = viewModelScope.launch {
        productRepository.save(product)
    }

    fun saveAll(products: List<Product>) = viewModelScope.launch {
        productRepository.saveAll(products)
    }

    fun callList(): MutableLiveData<List<User>> {
        val users = MutableLiveData<List<User>>()

        _loading.value = true
        viewModelScope.launch {
            try {
                val userList = productRepository.callList()
                users.postValue(userList)
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
            }
        }
        return users
    }

    fun buyList(): MutableLiveData<List<Product>> {
        val products = MutableLiveData<List<Product>>()

        _loading.value = true
        viewModelScope.launch {
            try {
                val productList = productRepository.buyList()
                products.postValue(productList)
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
            }

        }
        return products
    }
}