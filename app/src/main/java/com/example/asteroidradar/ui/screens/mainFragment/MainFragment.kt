package com.example.asteroidradar.ui.screens.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.main.AsteroidItemClickListener
import com.udacity.asteroidradar.main.AsteroidListAdapter

class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // recycle view
        val adapter = AsteroidListAdapter(AsteroidItemClickListener { asteroidId ->
            viewModel.onAsteroidItemClick(asteroidId)
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            adapter.submitList(asteroids)
        }

        // navigation
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner) { asteroid ->
            asteroid?.let {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(
                        it
                    )
                )
                viewModel.onDetailFragmentNavigated()
            }
        }

        return binding.root
    }

}