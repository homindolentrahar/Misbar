package com.homindolentrahar.misbar.ui.genres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.misbar.databinding.FragmentGenresBinding
import com.homindolentrahar.misbar.domain.models.LocalGenresModel
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.ui.movies.MoviesListItemAdapter
import com.homindolentrahar.misbar.ui.movies.MoviesViewModel
import com.homindolentrahar.misbar.ui.shows.ShowsListItemAdapter
import com.homindolentrahar.misbar.ui.shows.ShowsViewModel
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.helpers.bindImageRes

enum class GenresFragmentType { Movies, Shows }

class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private lateinit var moviesAdapter: MoviesListItemAdapter
    private lateinit var showsAdapter: ShowsListItemAdapter
    private lateinit var args: Bundle
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var showsViewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        showsViewModel = ViewModelProvider(requireActivity()).get(ShowsViewModel::class.java)
//        Init back
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
//        Populate data
        populateData()
    }

    private fun populateData() {
//        Get data
        args = requireArguments()
        val genresId = args.getParcelable<LocalGenresModel>("genre")!!.id
        val name = args.getParcelable<LocalGenresModel>("genre")!!.name
        val description = args.getParcelable<LocalGenresModel>("genre")!!.description
        val image = args.getParcelable<LocalGenresModel>("genre")!!.imageRes
        val type = args.getString("type")
//        Banner
        binding.tvName.text = name
        binding.tvDescription.text = description
        bindImageRes(binding.imgBackdrop, image)
//        List
        moviesAdapter = MoviesListItemAdapter { movie ->
            moviesViewModel.navigateToDetail(movie.id)
        }
        showsAdapter = ShowsListItemAdapter { show ->
            showsViewModel.navigateToDetail(show.id)
        }

        binding.rvGenres.apply {
            setHasFixedSize(true)
            adapter =
                if (type == GenresFragmentType.Movies.name)
                    moviesAdapter
                else
                    showsAdapter
        }

        if (type == GenresFragmentType.Movies.name) {
            EspressoIdlingResource.increment()
            moviesViewModel.getMoviesByGenre(genresId)

            moviesViewModel.moviesByGenre.observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingAnimation.loading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingAnimation.loading.visibility = View.GONE

                        moviesAdapter.submitData(lifecycle, resource.data!!.pagedListData)

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
        } else {
            EspressoIdlingResource.increment()
            showsViewModel.getShowsByGenre(genresId)

            showsViewModel.showsByGenre.observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loadingAnimation.loading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingAnimation.loading.visibility = View.GONE

                        showsAdapter.submitData(lifecycle,resource.data!!.pagedListData)

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
        }

//        Observe navigation
        moviesViewModel.detailNavigationData.observe(viewLifecycleOwner, { id ->
            if (id != -1) {
                val action = GenresFragmentDirections.actionGenresFragmentToDetailMoviesFragment(id)
                findNavController().navigate(action)
//                Clear navigation
                moviesViewModel.clearNavigateToDetail()
            }
        })
        showsViewModel.detailNavigationData.observe(viewLifecycleOwner, { id ->
            if (id != -1) {
                val action = GenresFragmentDirections.actionGenresFragmentToDetailShowsFragment(id)
                findNavController().navigate(action)
//                Clear navigation
                showsViewModel.clearNavigateToDetail()
            }
        })
    }
}