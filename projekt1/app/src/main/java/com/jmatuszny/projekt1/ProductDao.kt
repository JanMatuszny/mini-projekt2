package com.jmatuszny.projekt1

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE id=:id")
    fun getProduct(id: Int): Product

    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("DELETE FROM product WHERE id=:id")
    fun delete(id: Int)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Query("SELECT * FROM product ORDER BY id DESC LIMIT 1")
    fun getLastProductId(): Int
}