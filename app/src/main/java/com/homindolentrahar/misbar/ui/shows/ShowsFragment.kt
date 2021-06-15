package com.homindolentrahar.misbar.ui.shows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.misbar.databinding.FragmentShowsBinding
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.ui.core.MainFragmentDirections
import com.homindolentrahar.misbar.ui.genres.GenresFragmentType
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.transformers.ZoomOutPageTransformer

class ShowsFragment : Fragment() {

    private lateinit var binding: FragmentShowsBinding
    lateinit var viewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(ShowsViewModel::class.java)
//        Init carousel
        val carouselAdapter = ShowsCarouselItemAdapter { item ->
            viewModel.navigateToDetail(item.id)
        }
        binding.showsCarousel.apply {
            adapter = carouselAdapter
            setPageTransformer { page, position ->
                ZoomOutPageTransformer().transformPage(
                    page,
                    position
                )
            }
        }
//        Init list
        val listAdapter = ShowsListItemAdapter { item ->
            viewModel.navigateToDetail(item.id)
        }
        binding.rvPopularShows.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
//        Init genres banner
        val randomGenre = viewModel.randomGenres
        binding.genreBanner.item = randomGenre
        binding.genreBanner.btnCheck.setOnClickListener {
            viewModel.navigateToGenreFragment(randomGenre)
        }
//        Observe ShowsFragmentData
        EspressoIdlingResource.increment()
        viewModel.getShowsFragmentData()
        viewModel.showsFragmentData.observe(viewLifecycleOwner, { resource ->
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
                    binding.showsCarouselIndicator.setViewPager2(binding.showsCarousel)

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Error -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    Snackbar.make(
                        view.rootView,
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
                val action = MainFragmentDirections.actionMainFragmentToDetailShowsFragment(id)
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToDetail()
            }
        })
        viewModel.genreNavigationData.observe(viewLifecycleOwner, { item ->
            item?.let {
                val action = MainFragmentDirections.actionMainFragmentToGenresFragment(
                    type = GenresFragmentType.Shows.name,
                    genre = it
                )
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToGenreFragment()
            }
        })
    }
}