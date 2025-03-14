package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var totalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener { finish() }

        // ✅ Get Total Amount from Intent
        totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        if (totalAmount <= 0) {
            Toast.makeText(this, "Error: Invalid total amount!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.tvTotalAmount.text = "Total: $$totalAmount"

        // 🟢 Proceed to Payment
        binding.btnProceedToPayment.setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, PaymentActivity::class.java).apply {
                    putExtra("TOTAL_AMOUNT", totalAmount)
                    putExtra("USER_NAME", binding.etName.text.toString().trim())
                    putExtra("USER_EMAIL", binding.etEmail.text.toString().trim())
                    putExtra("USER_PHONE", binding.etPhone.text.toString().trim())
                    putExtra("USER_ADDRESS", binding.etAddress.text.toString().trim())
                }
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()

        if (TextUtils.isEmpty(name)) {
            binding.etName.error = "Enter your full name"
            return false
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Enter a valid email"
            return false
        }

        if (TextUtils.isEmpty(phone) || phone.length < 10) {
            binding.etPhone.error = "Enter a valid phone number"
            return false
        }

        if (TextUtils.isEmpty(address)) {
            binding.etAddress.error = "Enter your delivery address"
            return false
        }

        return true
    }
}
