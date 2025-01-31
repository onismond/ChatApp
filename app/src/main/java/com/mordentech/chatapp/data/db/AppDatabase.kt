package com.mordentech.chatapp.data.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mordentech.chatapp.data.db.entities.*

@Database(
    entities = [User::class, Profile::class,],
    version = 1,
)

abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao() : UserDao

    abstract fun getProfileDao() : ProfileDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ChatAppDatabase.db"
            ).build()
    }
}