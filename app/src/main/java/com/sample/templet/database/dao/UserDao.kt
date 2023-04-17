package com.sample.templet.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sample.templet.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<UserEntity>

    @Insert
    suspend fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}