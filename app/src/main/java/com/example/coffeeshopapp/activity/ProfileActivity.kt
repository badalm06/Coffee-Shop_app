package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshopapp.R
import com.example.coffeeshopapp.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileImage = findViewById<ImageView>(R.id.profile_image)
        val userName = findViewById<TextView>(R.id.user_name)
        val userEmail = findViewById<TextView>(R.id.user_email)
        val logoutButton = findViewById<Button>(R.id.logout_button)

        // Dummy Data
        userName.text = "John Doe"
        userEmail.text = "johndoe@email.com"

        binding.logoutButton.setOnClickListener{
            Firebase.auth.signOut()
//            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
//            getClient(this,gso).signOut()

            val intent = Intent(this, loginInActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
        }


    }
}
