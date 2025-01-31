package com.mordentech.chatapp.data.db.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Profile (
    @PrimaryKey
    var id: String,
    var name: String,
    var avatar: String?,
    var recentMessage: String = "",
    var messageDate: String = "",
)