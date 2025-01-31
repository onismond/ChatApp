package com.mordentech.chatapp

import android.app.Application
import com.mordentech.chatapp.data.db.AppDatabase
import com.mordentech.chatapp.data.network.AuthTokenInterceptor
import com.mordentech.chatapp.data.network.MyApi
import com.mordentech.chatapp.data.network.NetworkConnectionInterceptor
import com.mordentech.chatapp.data.preferences.PreferenceProvider
import com.mordentech.chatapp.data.repositories.HomeRepository
import com.mordentech.chatapp.data.repositories.UserRepository
import com.mordentech.chatapp.ui.auth.AuthViewModelFactory
import com.mordentech.chatapp.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ChatAppApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ChatAppApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AuthTokenInterceptor(instance()) }
        bind() from singleton { MyApi(instance(), instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }

}