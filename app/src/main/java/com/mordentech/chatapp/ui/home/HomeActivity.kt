package com.mordentech.chatapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mordentech.chatapp.R
import com.mordentech.chatapp.databinding.ActivityHomeBinding
import com.mordentech.chatapp.ui.auth.AuthViewModelFactory
import com.mordentech.chatapp.util.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var navController: NavController

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.chatsFragment, R.id.updatesFragment, R.id.communitiesFragment, R.id.callsFragment)
            .build()
        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}