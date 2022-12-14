package com.example.asteroidradar.data.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteroidradar.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class AsteroidEntity(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)
