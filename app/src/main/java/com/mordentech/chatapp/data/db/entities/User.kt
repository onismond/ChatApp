package com.mordentech.chatapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var username: String? = null,
    var email: String? = null,
    var contact: String? = null,
    var avatar: String? = null,
    var token: String? = null,
){
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}