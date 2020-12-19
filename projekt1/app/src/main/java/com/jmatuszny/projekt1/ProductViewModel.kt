package com.jmatuszny.projekt1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: ProductRepo
    val products: LiveData<List<Product>>

    init {
        repo = ProductRepo(ProductDB.getDatabase(application.applicationContext).productDao())
        products = repo.allProducts
    }

    fun getProduct(id: Int) = repo.getProduct(id)

    fun insert(product: Product) = repo.insert(product)

    fun update(product: Product) = repo.update(product)

    fun delete(id: Int) = repo.delete(id)

    fun deleteAll() = repo.deleteAll()

    fun getLastProductId() = repo.getLastProductId()
}