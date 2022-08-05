package com.example.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asteroidradar.utils.DatabaseConstants

@Dao
interface IAsteroidDao {
    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} ORDER by closeApproachDate")
    fun getAll(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<AsteroidEntity>)
}