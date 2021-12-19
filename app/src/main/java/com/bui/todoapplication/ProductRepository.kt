package com.bui.todoapplication

import androidx.annotation.WorkerThread
import com.bui.todoapplication.local.ProductDao
import com.bui.todoapplication.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: Flow<List<Product>> = productDao.loadAll()

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
}