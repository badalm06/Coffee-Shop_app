package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class signUpActivity : AppCompatActivity() {
    private val binding : ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

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


        binding.signInButton.setOnClickListener{
            startActivity(Intent(this, loginInActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener{

            // Get text from edit text field
            val email = binding.email.text.toString()
            val username = binding.userName.text.toString()
            val password = binding.password.text.toString()
            val repeatPassword = binding.repeatPassword.text.toString()

            // Check if all field is filled or not
            if(email.isEmpty() || username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the Details", Toast.LENGTH_SHORT).show()
            }
            else if(password != repeatPassword) {
                Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, loginInActivity::class.java))
                            finish()
                        }
                        else {
                            Toast.makeText(this, "Registration failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}