package com.udacity.asteroidradar.main

import com.example.asteroidradar.data.model.Asteroid


class AsteroidItemClickListener(val clickListener: (Asteroid) -> Unit) {
    fun onClick(data: Asteroid) = clickListener(data)
}