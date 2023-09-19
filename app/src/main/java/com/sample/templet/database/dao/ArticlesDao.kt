package com.sample.templet.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sample.templet.main.model.ArticlesModel

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ArticlesModel?>?)
}