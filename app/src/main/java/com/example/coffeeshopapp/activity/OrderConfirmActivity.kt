package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivityOrderConfirmBinding


class OrderConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // ✅ Get order details from intent
        val orderId = intent.getStringExtra("ORDER_ID") ?: "N/A"
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest"

        // ✅ Set order details
        binding.tvOrderId.text = "Order ID: $orderId"
        binding.tvOrderAmount.text = "Total: $$totalAmount"
        binding.tvUserName.text = "Thank you, $userName!"

        // ✅ Back to home button
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
