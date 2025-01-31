package com.mordentech.chatapp.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mordentech.chatapp.databinding.ActivityWelcomeBinding
import com.mordentech.chatapp.ui.auth.login.LoginActivity
import com.mordentech.chatapp.ui.auth.signup.SignupActivity
import org.kodein.di.KodeinAware

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignup.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, SignupActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}