package com.example.coffeeshopapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivityLoginInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class loginInActivity : AppCompatActivity() {
    private val binding: ActivityLoginInBinding by lazy {
        ActivityLoginInBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        // Check if user already login in
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(userName, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Login Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, signUpActivity::class.java))
            finish()
        }
        binding.forgotPassword.setOnClickListener {
            val email = binding.userName.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your registered email", Toast.LENGTH_SHORT).show()
            } else {
                resetPassword(email)
            }
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}
