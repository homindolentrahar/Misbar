package com.homindolentrahar.misbar.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.misbar.databinding.FragmentMoviesBinding
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.ui.core.MainFragmentDirections
import com.homindolentrahar.misbar.ui.genres.GenresFragmentType
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.transformers.ZoomOutPageTransformer

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
//        Init carousel
        val carouselAdapter = MoviesCarouselItemAdapter { item ->
            viewModel.navigateToDetail(item.id)
        }
        binding.moviesCarousel.apply {
            adapter = carouselAdapter
            setPageTransformer { page, position ->
                ZoomOutPageTransformer().transformPage(
                    page,
                    position
                )
            }
        }
//        Init list
        val listAdapter = MoviesListItemAdapter { item ->
            viewModel.navigateToDetail(item.id)
        }
        binding.rvPopularMovies.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
//        Init genres banner
        val randomGenre = viewModel.randomGenres
        binding.genreBanner.item = randomGenre
        binding.genreBanner.btnCheck.setOnClickListener {
            viewModel.navigateToGenreFragment(randomGenre)
        }
//        Search Functionality
//        EspressoIdlingResource.increment()
//        viewModel.searchMovies(query)
//        Observe MoviesFragmentData
        EspressoIdlingResource.increment()
        viewModel.getMoviesFragmentData()
        viewModel.moviesFragmentData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingAnimation.loading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    val carouselData = resource.data?.listData
                    val popularData = resource.data?.pagedListData

                    carouselAdapter.submitList(carouselData)
                    listAdapter.submitData(lifecycle, popularData!!)
                    binding.moviesCarouselIndicator.setViewPager2(binding.moviesCarousel)

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Error -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    Snackbar.make(
                        requireView(),
                        resource.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            }
        })
//        Observe MoviesSearchData
        viewModel.moviesSearchData.observe(viewLifecycleOwner,{resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingAnimation.loading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    val searchData = resource.data?.pagedListData

                    listAdapter.submitData(lifecycle, searchData!!)

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Error -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    Snackbar.make(
                        requireView(),
                        resource.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            }
        })
//        Observe Navigation
        viewModel.detailNavigationData.observe(viewLifecycleOwner, { id ->
            if (id != -1) {
                val action = MainFragmentDirections.actionMainFragmentToDetailMoviesFragment(id)
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToDetail()
            }
        })
        viewModel.genreNavigationData.observe(viewLifecycleOwner, { item ->
            item?.let {
                val action = MainFragmentDirections.actionMainFragmentToGenresFragment(
                    type = GenresFragmentType.Movies.name,
                    genre = item
                )
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToGenreFragment()
            }
        })
    }
}