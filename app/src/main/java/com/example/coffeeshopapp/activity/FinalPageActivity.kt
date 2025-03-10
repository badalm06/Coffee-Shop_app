package com.example.coffeeshopapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R

class FinalPageActivity : AppCompatActivity() {

    private lateinit var tvSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_page)

        tvSummary = findViewById(R.id.tvSummary)

        // Retrieve user details from Intent
        val name = intent.getStringExtra("USER_NAME")
        val email = intent.getStringExtra("USER_EMAIL")
        val phone = intent.getStringExtra("USER_PHONE")
        val address = intent.getStringExtra("USER_ADDRESS")

        // Display order summary
        tvSummary.text = "Name: $name\nEmail: $email\nPhone: $phone\nAddress: $address\n\nOrder Confirmed! 🎉"
    }
}
