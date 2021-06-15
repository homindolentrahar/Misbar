package com.homindolentrahar.misbar.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.databinding.FragmentFavoritesBinding
import com.homindolentrahar.misbar.others.constants.FavoriteItemType
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.ui.core.MainFragmentDirections
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
//        Init List
        val listAdapter = FavoritesListItemAdapter { item ->
            viewModel.navigateToDetail(item.id, item.type)
        }
        binding.rvFavorites.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
//        Observe FavoritesData
        EspressoIdlingResource.increment()
        viewModel.getFavoritesData()
        binding.filtersChip.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.filters_all_chip -> viewModel.getFavoritesData()
                R.id.filters_movies_chip -> viewModel.getFavoritesDataByType(FavoriteItemType.Movies.value)
                R.id.filters_shows_chip -> viewModel.getFavoritesDataByType(FavoriteItemType.Shows.value)
            }
        }
        viewModel.favoritesData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingAnimation.loading.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Success -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    resource.data?.let {
                        listAdapter.submitData(lifecycle, it)
                        Log.d(
                            FavoritesFragment::class.java.simpleName,
                            listAdapter.itemCount.toString()
                        )
                    }

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            }
        })
//        Observe Navigation
        viewModel.detailNavigationData.observe(viewLifecycleOwner, { navData ->
            if (navData != null) {
                val action = if (navData.type == FavoriteItemType.Movies)
                    MainFragmentDirections.actionMainFragmentToDetailMoviesFragment(navData.itemId)
                else
                    MainFragmentDirections.actionMainFragmentToDetailShowsFragment(navData.itemId)
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToDetail()
            }
        })
    }
}