package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.data.model.Asteroid
import com.example.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidViewHolder private constructor(private val binding: ListItemAsteroidBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Asteroid, clickListener: AsteroidItemClickListener) {
        binding.asteroid = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) : AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }
}