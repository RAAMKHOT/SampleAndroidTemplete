package com.sample.templet.database.appdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.templet.database.Converters
import com.sample.templet.database.dao.ArticlesDao
import com.sample.templet.database.dao.UserDao
import com.sample.templet.database.entity.UserEntity
import com.sample.templet.main.model.ArticlesModel

@Database(entities = [(UserEntity::class), (ArticlesModel::class)], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articlesDao(): ArticlesDao

    companion object {
    }
}