package com.mordentech.chatapp.data.repositories

import com.mordentech.chatapp.data.db.AppDatabase
import com.mordentech.chatapp.data.db.entities.User
import com.mordentech.chatapp.data.network.MyApi
import com.mordentech.chatapp.data.network.SafeApiRequest
import com.mordentech.chatapp.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun userLogin(username: String, password: String) : AuthResponse {
        return apiRequest { api.userLogin(username=username, password=password) }
    }

    suspend fun userSignup(
        name: String,
        username: String,
        email: String,
        password: String
    ): AuthResponse {
        return apiRequest { api.userSignup(name=name, username=username, email=email, password=password) }
    }

    fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

    fun getUserToken() = db.getUserDao().getUserToken()

}