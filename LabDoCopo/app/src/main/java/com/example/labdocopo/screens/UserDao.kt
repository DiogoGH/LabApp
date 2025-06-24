package com.example.labdocopo.data

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: User)

    @Query("SELECT * FROM users WHERE nome = :nome AND password = :password")
    suspend fun login(nome: String, password: String): User?

    @Query("SELECT * FROM users WHERE nome = :nome")
    suspend fun getByNome(nome: String): User?
}
