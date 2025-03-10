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
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener { finish() }


        recyclerView = findViewById(R.id.recyclerCartItems)  // Corrected ID
        recyclerView.layoutManager = LinearLayoutManager(this)

        cartAdapter = CartAdapter(CartManager.getCartItems() as MutableList<ItemsModel>)
        recyclerView.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()

    }

}
