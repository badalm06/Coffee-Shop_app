package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileImage = findViewById<ImageView>(R.id.profile_image)
        val userName = findViewById<TextView>(R.id.user_name)
        val userEmail = findViewById<TextView>(R.id.user_email)
        val logoutButton = findViewById<Button>(R.id.logout_button)

        // Dummy Data
        userName.text = "John Doe"
        userEmail.text = "johndoe@email.com"

        logoutButton.setOnClickListener {
            // Handle logout action (clear session, go back to login)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
