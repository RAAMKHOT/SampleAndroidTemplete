package com.sample.templet.database.appdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.templet.database.Converters
import com.sample.templet.database.dao.UserDao
import com.sample.templet.database.entity.UserEntity

@Database(entities = [(UserEntity::class)], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
    }
}