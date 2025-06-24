package com.example.labdocopo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drink: Drink)

    @Query("SELECT * FROM drinks")
    fun getAll(): Flow<List<Drink>>

    @Delete
    suspend fun delete(drink: Drink)
}
