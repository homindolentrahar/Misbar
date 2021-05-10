package com.homindolentrahar.misbar.ui.core

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homindolentrahar.misbar.ui.movies.MoviesFragment
import com.homindolentrahar.misbar.ui.shows.ShowsFragment

class MainScreenPageAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf(
        MoviesFragment(),
        ShowsFragment(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}