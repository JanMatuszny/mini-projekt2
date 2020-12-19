package com.jmatuszny.projekt1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(@PrimaryKey (autoGenerate = true)
                   var id: Int = 0,
                   var name: String,
                   var price: Int,
                   var amount: Int,
                   var isBought: Boolean) {

}