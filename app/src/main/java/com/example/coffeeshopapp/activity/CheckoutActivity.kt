package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R

class CheckoutActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnProceedToPayment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Initialize UI elements
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        btnProceedToPayment = findViewById(R.id.btnProceedToPayment)

        // Set button click listener
        btnProceedToPayment.setOnClickListener {
            validateAndProceed()
        }
    }

    private fun validateAndProceed() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val address = etAddress.text.toString().trim()

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            etName.error = "Enter your full name"
            return
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email"
            return
        }

        if (TextUtils.isEmpty(phone) || phone.length < 10) {
            etPhone.error = "Enter a valid phone number"
            return
        }

        if (TextUtils.isEmpty(address)) {
            etAddress.error = "Enter your delivery address"
            return
        }

        // If validation is successful, proceed to FinalPageActivity
        val intent = Intent(this, FinalPageActivity::class.java).apply {
            putExtra("USER_NAME", name)
            putExtra("USER_EMAIL", email)
            putExtra("USER_PHONE", phone)
            putExtra("USER_ADDRESS", address)
        }
        startActivity(intent)
    }
}
