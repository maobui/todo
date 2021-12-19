package com.bui.todoapplication

import androidx.annotation.WorkerThread
import com.bui.todoapplication.local.ProductDao
import com.bui.todoapplication.model.Product
import com.bui.todoapplication.model.User
import com.bui.todoapplication.remote.TodoApi
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao, private val todoApi: TodoApi) {

    val productsSaved: Flow<List<Product>> = productDao.loadAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun save(product: Product) {
        productDao.insert(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveAll(products: List<Product>) {
        productDao.insertAll(products)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun callList(): List<User> {
        return todoApi.callList()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun buyList(): List<Product> {
        return todoApi.buyList()
    }
}