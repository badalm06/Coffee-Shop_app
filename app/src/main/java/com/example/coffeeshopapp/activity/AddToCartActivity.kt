package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.adapters.CartAdapter
import com.example.coffeeshopapp.databinding.ActivityAddToCartBinding
import com.example.coffeeshopapp.model.ItemsModel
import com.example.coffeeshopapp.utils.CartManager

class AddToCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddToCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private var cartItems: MutableList<ItemsModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back Button Click
        binding.btnBack.setOnClickListener {
            finish() // Closes Add to Cart and goes back
        }

        // Proceed to Checkout
        binding.btnCheckout.setOnClickListener {
            val total = cartItems.sumOf { it.price * it.quantity } // Correct total calculation

            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("TOTAL_AMOUNT", total)  // ✅ Pass the total amount
            startActivity(intent)
        }

        recyclerView = binding.recyclerCartItems
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartItems = CartManager.getCartItems() as MutableList<ItemsModel>
        cartAdapter = CartAdapter(cartItems) { updateTotalPrice() }
        recyclerView.adapter = cartAdapter

        // Initialize total price
        updateTotalPrice()
    }

    // Function to update the total price dynamically
    private fun updateTotalPrice() {
        val total = cartItems.sumOf { it.price * it.quantity }  // Assuming price & quantity exist
        binding.tvTotalPrice.text = "Total: $$total"
    }
}
