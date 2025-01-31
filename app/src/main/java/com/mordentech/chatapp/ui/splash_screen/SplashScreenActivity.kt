package com.mordentech.chatapp.ui.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mordentech.chatapp.databinding.ActivitySplashScreenBinding
import com.mordentech.chatapp.ui.auth.AuthViewModel
import com.mordentech.chatapp.ui.auth.AuthViewModelFactory
import com.mordentech.chatapp.ui.home.HomeActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getLoggedInUser().observe(this) { user ->
                if (user == null) {
                    intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1000)
    }
}