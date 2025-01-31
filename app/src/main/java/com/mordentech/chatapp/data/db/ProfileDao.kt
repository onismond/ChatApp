package com.mordentech.chatapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mordentech.chatapp.data.db.entities.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(profile: Profile): Long

    @Delete
    fun remove(profile: Profile): Int

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: String) : LiveData<Profile>

    @Query("SELECT * FROM profile ORDER BY id DESC")
    fun getProfiles() : LiveData<MutableList<Profile>>

    @Query("DELETE FROM profile")
    fun removeProfiles()

}