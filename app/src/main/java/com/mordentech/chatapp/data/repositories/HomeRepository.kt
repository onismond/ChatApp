package com.mordentech.chatapp.data.repositories

import com.mordentech.chatapp.data.db.AppDatabase
import com.mordentech.chatapp.data.db.entities.*
import com.mordentech.chatapp.data.network.MyApi
import com.mordentech.chatapp.data.network.SafeApiRequest
import com.mordentech.chatapp.data.network.responses.*
import okhttp3.MultipartBody

class HomeRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun home(): DefaultResponse {
        return apiRequest { api.home() }
    }

}