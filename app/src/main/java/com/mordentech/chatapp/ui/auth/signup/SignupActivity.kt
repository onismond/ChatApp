package com.mordentech.chatapp.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mordentech.chatapp.databinding.ActivitySignupBinding
import com.mordentech.chatapp.ui.auth.AuthViewModel
import com.mordentech.chatapp.ui.auth.AuthViewModelFactory
import com.mordentech.chatapp.ui.auth.login.LoginActivity
import com.mordentech.chatapp.ui.home.HomeActivity
import com.mordentech.chatapp.util.ApiException
import com.mordentech.chatapp.util.Coroutines
import com.mordentech.chatapp.util.NoInternetException
import com.mordentech.chatapp.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.getLoggedInUser().observe(this) { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
        binding.buttonSignup.setOnClickListener {
            signup()
        }
        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }

    }

    private fun signup() {
        val name = binding.editTextName.text.toString().trim()
        val username = binding.editTextUsername.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString()
        val passwordConfirm = binding.editTextConfirmPassword.text.toString()

        if (name.isEmpty()) {
            binding.root.snackbar("Please enter your name")
            return
        }
        if (email.isEmpty()) {
            binding.root.snackbar("Please enter your email")
            return
        }
        if (password.isEmpty()) {
            binding.root.snackbar("Please enter a password")
            return
        }
        if (passwordConfirm.isEmpty()) {
            binding.root.snackbar("Please confirm your password")
            return
        }
        if (password != passwordConfirm) {
            binding.root.snackbar("Password does not match")
            return
        }

        Coroutines.io {
            try {
                val authResponse = viewModel.userSignup(name=name, username=username, email=email, password=password)
                if (authResponse.success == true) {
                    runOnUiThread {
                        binding.root.snackbar(authResponse.detail)
                        startLoginActivity()
                    }
                } else {
                    runOnUiThread { binding.root.snackbar(authResponse.detail) }
                }
            } catch (e: ApiException) {
                runOnUiThread {
                    binding.root.snackbar(e.message.toString())
                }
            } catch (e: NoInternetException) {
                runOnUiThread {
                    binding.root.snackbar(e.message.toString())
                }
            } catch (e: Exception) {
                runOnUiThread {
                    binding.root.snackbar(e.message.toString())
                }
            }
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
















