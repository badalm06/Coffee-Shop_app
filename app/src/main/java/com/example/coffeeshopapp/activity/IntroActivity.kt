package com.example.coffeeshopapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.coffeeshopapp.R

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, loginInActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}