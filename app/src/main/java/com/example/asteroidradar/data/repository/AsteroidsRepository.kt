package com.example.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.data.api.AsteroidApi
import com.example.asteroidradar.data.api.asAsteroidEntities
import com.example.asteroidradar.data.model.Asteroid
import com.example.asteroidradar.data.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.dao.getAll()) {
            it.asAsteroids()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = AsteroidApi.getAsteroids()
            database.dao.insertAll(asteroids.asAsteroidEntities())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = AsteroidApi.getPictureOfDay()
        }
        return pictureOfDay
    }
}