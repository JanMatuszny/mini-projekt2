package com.jmatuszny.projekt1

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductViewModel(application: Application) : AndroidViewModel(application) {

//    private val repo: ProductRepo
//    val products: LiveData<List<Product>>
//
//    init {
//        repo = ProductRepo(ProductDB.getDatabase(application.applicationContext).productDao())
//        products = repo.allProducts
//    }
//
//    fun getProduct(id: Int) = repo.getProduct(id)
//
//    fun insert(product: Product) = repo.insert(product)
//
//    fun update(product: Product) = repo.update(product)
//
//    fun delete(id: Int) = repo.delete(id)
//
//    fun deleteAll() = repo.deleteAll()
//
//    fun getLastProductId() = repo.getLastProductId()
//
//    private suspend fun readFromdb() {
//        val ref = database.getReference("products")
//        var name: String
//        var surname: String
//        var age: Long
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for( messageSnapshot in dataSnapshot.children) {
//                    name = messageSnapshot.child("name").value as String
//                    surname = messageSnapshot.child("surname").value as String
//                    age = messageSnapshot.child("age").value as Long
//                    Log.i("readDB", "$name $surname $age")
//                }
//            }
//            override fun onCancelled(db: FirebaseDatabase) {
//                Log.e("readDB-error", databaseError.details)
//            }
//        })
//    }
}