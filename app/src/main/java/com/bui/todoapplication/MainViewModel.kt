package com.bui.todoapplication

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bui.todoapplication.local.TodoRoomDatabase
import com.bui.todoapplication.model.Product
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : ViewModel() {

    private val database by lazy { TodoRoomDatabase.getDatabase(application) }
    private val productRepository by lazy { ProductRepository(database.productDao()) }

    val allProducts: LiveData<List<Product>> = productRepository.allProducts.asLiveData()

    fun save(product: Product) = viewModelScope.launch {
        productRepository.save(product)
    }

    fun saveAll(products: List<Product>) = viewModelScope.launch {
        productRepository.saveAll(products)
    }
}