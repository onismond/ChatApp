package com.mordentech.chatapp.data.network.responses

import com.mordentech.chatapp.data.db.entities.User

data class AuthResponse (
    val success: Boolean?,
    val detail: String = "",
    val token: String?,
    val user: User?,
)
