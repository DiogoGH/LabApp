package com.example.labdocopo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drink::class], version = 1)
abstract class LabDoCopoDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao

    companion object {
        @Volatile private var INSTANCE: LabDoCopoDatabase? = null

        fun getDatabase(context: Context): LabDoCopoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LabDoCopoDatabase::class.java,
                    "labdocopo_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
