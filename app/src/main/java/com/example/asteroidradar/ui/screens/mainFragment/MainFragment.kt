package com.example.asteroidradar.ui.screens.mainFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_rent_menu -> {
                viewModel.asteroids
            }
            R.id.show_all_menu -> {
                viewModel.asteroids
            }
            R.id.show_buy_menu -> {
                viewModel.asteroids
            }
        }
        return true
    }
}