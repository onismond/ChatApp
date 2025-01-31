package com.mordentech.chatapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mordentech.chatapp.data.db.entities.CURRENT_USER_ID
import com.mordentech.chatapp.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User): Long

    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getuser() : LiveData<User>

    @Query("DELETE FROM user")
    fun removeUser()

    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getCurrentUser() : User

    @Query("SELECT token FROM user WHERE uid = $CURRENT_USER_ID")
    fun getUserToken() : String

    @Query("SELECT avatar FROM user WHERE uid = $CURRENT_USER_ID")
    fun getAvatar() : LiveData<String>

}