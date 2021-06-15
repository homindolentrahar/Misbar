package com.homindolentrahar.misbar.ui.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.homindolentrahar.misbar.ui.favorite.FavoritesFragment
import com.homindolentrahar.misbar.ui.genres.GenresFragment
import com.homindolentrahar.misbar.ui.movies.DetailMoviesFragment
import com.homindolentrahar.misbar.ui.movies.MoviesFragment
import com.homindolentrahar.misbar.ui.search.SearchFragment
import com.homindolentrahar.misbar.ui.shows.DetailShowsFragment
import com.homindolentrahar.misbar.ui.shows.ShowsFragment
import com.homindolentrahar.misbar.ui.splash.SplashFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AppFragmentFactory @Inject constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SplashFragment::class.java.simpleName -> SplashFragment()
            MainFragment::class.java.name -> MainFragment()
            MoviesFragment::class.java.name -> MoviesFragment()
            ShowsFragment::class.java.name -> ShowsFragment()
            FavoritesFragment::class.java.name -> FavoritesFragment()
            GenresFragment::class.java.name -> GenresFragment()
            SearchFragment::class.java.name -> SearchFragment()
            DetailMoviesFragment::class.java.name -> DetailMoviesFragment()
            DetailShowsFragment::class.java.name -> DetailShowsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}