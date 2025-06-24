package com.example.labdocopo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class LabDoCopoUserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: LabDoCopoUserDatabase? = null

        fun getDatabase(context: Context): LabDoCopoUserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LabDoCopoUserDatabase::class.java,
                    "labdocopo_users_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
