package com.jmatuszny.projekt1

data class Product(var name: String = "",
                   var price: Long = 0,
                   var amount: Long = 0,
                   var isBought: Boolean = false) {
}