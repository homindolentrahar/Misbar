package com.homindolentrahar.misbar.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.homindolentrahar.misbar.databinding.FragmentMoviesBinding
import com.homindolentrahar.misbar.ui.core.CarouselItemAdapter
import com.homindolentrahar.misbar.ui.core.ListItemAdapter
import com.homindolentrahar.misbar.ui.core.MainFragmentDirections
import com.homindolentrahar.misbar.ui.detail.DetailItemType
import com.homindolentrahar.misbar.utils.transformers.ZoomOutPageTransformer

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MoviesViewModel::class.java]
//        Init carousel
        val carouselAdapter = CarouselItemAdapter { item ->
            val action = MainFragmentDirections.actionMainFragmentToDetailItemFragment(
                DetailItemType.Movies.type,
                item.id
            )
            findNavController().navigate(action)
        }
        carouselAdapter.submitList(viewModel.getMovies().take(5))
        binding.moviesCarousel.apply {
            adapter = carouselAdapter
            setPageTransformer { page, position ->
                ZoomOutPageTransformer().transformPage(
                    page,
                    position
                )
            }
        }
        binding.moviesCarouselIndicator.setViewPager2(binding.moviesCarousel)

//        Init list
        val listAdapter = ListItemAdapter { item ->
            val action = MainFragmentDirections.actionMainFragmentToDetailItemFragment(
                DetailItemType.Movies.type,
                item.id
            )
            findNavController().navigate(action)
        }
        val movies = viewModel.getMovies().subList(5, viewModel.getMovies().size)

        listAdapter.submitList(movies)
        binding.rvPopularMovies.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }
}