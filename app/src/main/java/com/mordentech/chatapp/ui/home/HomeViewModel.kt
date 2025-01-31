package com.mordentech.chatapp.ui.home

import androidx.lifecycle.ViewModel
import com.mordentech.chatapp.data.db.entities.*
import com.mordentech.chatapp.data.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    suspend fun home() = withContext(Dispatchers.IO) {repository.home()}

}