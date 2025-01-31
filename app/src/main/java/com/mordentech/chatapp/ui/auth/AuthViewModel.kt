package com.mordentech.chatapp.ui.auth

import androidx.lifecycle.ViewModel
import com.mordentech.chatapp.data.db.entities.User
import com.mordentech.chatapp.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        username: String,
        password: String,
    ) = withContext(Dispatchers.IO){repository.userLogin(username=username, password=password)}

    suspend fun userSignup(
        name: String,
        username: String,
        email: String,
        password: String,
    ) = withContext(Dispatchers.IO){repository.userSignup(name=name, username=username, email=email, password=password)}

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)


}