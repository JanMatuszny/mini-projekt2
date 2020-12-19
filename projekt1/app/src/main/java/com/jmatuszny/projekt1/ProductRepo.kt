package com.jmatuszny.projekt1

class ProductRepo(private val productDao: ProductDao) {

    val allProducts = productDao.getProducts()

    fun getProduct(id: Int) = productDao.getProduct(id)

    fun insert(product: Product) = productDao.insert(product)

    fun update(product: Product) = productDao.update(product)

    fun delete(id: Int) = productDao.delete(id)

    fun deleteAll() = productDao.deleteAll()

    fun getLastProductId() = productDao.getLastProductId()
}