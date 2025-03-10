package com.example.coffeeshopapp.utils

import com.example.coffeeshopapp.model.CartItem
import com.example.coffeeshopapp.model.ItemsModel

object CartManager {
    private val cartItems = mutableListOf<ItemsModel>()
    val cartList = mutableListOf<CartItem>() // Ensure cartList is a valid list


    fun addToCart(newItem: ItemsModel) {
        val existingItem = cartItems.find { it.title == newItem.title && it.size == newItem.size }

        if (existingItem != null) {
            // Agar same item aur same size hai to bas quantity badhao
            existingItem.quantity += 1
        } else {
            // Naya item add karo agar exist nahi karta
            cartItems.add(newItem)
        }
    }

    fun getCartItems(): List<ItemsModel> {
        return cartItems
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val item = cartItems.find { it.id == productId }
        item?.quantity = quantity
    }

    fun removeItem(itemId: String) {
        val iterator = cartList.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == itemId) {
                iterator.remove()
                break // Ensure only the first matching item is removed
            }
        }
    }




}
