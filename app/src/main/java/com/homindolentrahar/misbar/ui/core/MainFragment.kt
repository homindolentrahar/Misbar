package com.homindolentrahar.misbar.ui.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.homindolentrahar.misbar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var screenAdapter: MainScreenPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Implement ViewPager
        screenAdapter = MainScreenPageAdapter(this)
        binding.viewPager.adapter = screenAdapter

        val tabTitles = listOf("Movies", "Shows", "Favorites")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
//        Navigate to search
        binding.btnSearch.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }
    }
}