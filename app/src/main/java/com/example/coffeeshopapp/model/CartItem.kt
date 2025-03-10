package com.example.coffeeshopapp.model

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    var quantity: Int,
    var size: String
)
