package com.mordentech.chatapp.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mordentech.chatapp.R
import com.mordentech.chatapp.databinding.ActivityLoginBinding
import com.mordentech.chatapp.ui.auth.AuthViewModel
import com.mordentech.chatapp.ui.auth.AuthViewModelFactory
import com.mordentech.chatapp.ui.auth.signup.SignupActivity
import com.mordentech.chatapp.ui.home.HomeActivity
import com.mordentech.chatapp.util.ApiException
import com.mordentech.chatapp.util.Coroutines
import com.mordentech.chatapp.util.NoInternetException
import com.mordentech.chatapp.util.snackbar
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        binding.textViewRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString()

        // todo validate inputs

        if (username.isEmpty()) {
            binding.root.snackbar("Please enter your username")
            return
        }

        if (password.isEmpty()) {
            binding.root.snackbar("Please enter your password")
            return
        }

        Coroutines.io {
            try {
                val authResponse = viewModel.userLogin(username=username, password=password)
                if (authResponse.success == true && authResponse.user != null) {
                    val user = authResponse.user
                    user.token = authResponse.token
                    viewModel.saveLoggedInUser(user)
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
}