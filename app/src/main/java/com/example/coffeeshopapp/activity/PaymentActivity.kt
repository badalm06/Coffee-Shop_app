package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import kotlin.random.Random

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityPaymentBinding
    private var totalAmount: Double = 0.0
    private lateinit var orderId: String
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Initialize Razorpay SDK
        Checkout.preload(applicationContext)

        // ✅ Get total amount from intent
        totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        // ✅ Fetch actual user name (Replace with actual data fetching logic)
        userName = intent.getStringExtra("USER_NAME") ?: "Guest User"

        // ✅ Generate unique Order ID
        orderId = "ORD" + Random.nextInt(1000, 9999)  // Example: ORD5623
        // ✅ Update UI
        binding.tvTotalAmount.text = "Total: ₹$totalAmount"

        // ✅ Proceed button click
        binding.btnProceedPayment.setOnClickListener {
            handlePayment()
        }
    }

    private fun handlePayment() {
        val selectedId = binding.paymentMethodGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a payment method!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedMethod = findViewById<RadioButton>(selectedId).text.toString()

        when (selectedMethod) {
            "Cash on Delivery (COD)" -> processCOD()
            "Pay via Razorpay" -> processRazorpay()
        }
    }

    private fun processCOD() {
        Toast.makeText(this, "Order placed successfully with COD!", Toast.LENGTH_LONG).show()

        // ✅ Redirect to OrderConfirmActivity
        val intent = Intent(this, OrderConfirmActivity::class.java).apply {
            putExtra("ORDER_ID", orderId)
            putExtra("TOTAL_AMOUNT", totalAmount)
            putExtra("USER_NAME", userName)
            putExtra("PAYMENT_METHOD", "Cash on Delivery")
        }
        startActivity(intent)
        finish() // Close PaymentActivity
    }

    private fun processRazorpay() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_yourkey") // Replace with actual Razorpay Key

        val paymentDetails = JSONObject()
        try {
            paymentDetails.put("name", "Coffee Shop")
            paymentDetails.put("description", "Order Payment")
            paymentDetails.put("currency", "INR")
            paymentDetails.put("amount", totalAmount * 100) // Convert to paise

            val prefill = JSONObject()
            prefill.put("email", "user@example.com")
            prefill.put("contact", "9999999999")

            paymentDetails.put("prefill", prefill)

            checkout.open(this, paymentDetails)
        } catch (e: Exception) {
            Toast.makeText(this, "Payment Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // ✅ Handle Razorpay Payment Result
    override fun onPaymentSuccess(paymentId: String?) {
        Toast.makeText(this, "Payment Successful! ID: $paymentId", Toast.LENGTH_SHORT).show()

        // ✅ Redirect to OrderConfirmActivity
        val intent = Intent(this, OrderConfirmActivity::class.java).apply {
            putExtra("ORDER_ID", orderId)
            putExtra("TOTAL_AMOUNT", totalAmount)
            putExtra("USER_NAME", userName)
            putExtra("PAYMENT_METHOD", "Razorpay")
        }
        startActivity(intent)
        finish() // Close PaymentActivity
    }

    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(this, "Payment Failed: $response", Toast.LENGTH_LONG).show()

        // ✅ Redirect back to Checkout Page
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
        finish() // Close PaymentActivity
    }
}
