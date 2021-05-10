package com.homindolentrahar.misbar.ui.shows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.homindolentrahar.misbar.databinding.FragmentShowsBinding
import com.homindolentrahar.misbar.ui.core.MainFragmentDirections
import com.homindolentrahar.misbar.ui.detail.DetailItemType
import com.homindolentrahar.misbar.ui.core.CarouselItemAdapter
import com.homindolentrahar.misbar.ui.core.ListItemAdapter
import com.homindolentrahar.misbar.utils.transformers.ZoomOutPageTransformer

class ShowsFragment : Fragment() {
    private lateinit var binding: FragmentShowsBinding
    private lateinit var viewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init viewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ShowsViewModel::class.java]
//        Init carousel
        val carouselAdapter = CarouselItemAdapter { item ->
            val action = MainFragmentDirections.actionMainFragmentToDetailItemFragment(
                DetailItemType.Shows.type,
                item.id
            )
            findNavController().navigate(action)
        }
        carouselAdapter.submitList(viewModel.getShows().take(5))
        binding.showsCarousel.apply {
            adapter = carouselAdapter
            setPageTransformer { page, position ->
                ZoomOutPageTransformer().transformPage(
                    page,
                    position
                )
            }
        }
        binding.showsCarouselIndicator.setViewPager2(binding.showsCarousel)
//        Init list
        val listAdapter = ListItemAdapter { item ->
            val action = MainFragmentDirections.actionMainFragmentToDetailItemFragment(
                DetailItemType.Shows.type,
                item.id
            )
            findNavController().navigate(action)
        }
        val shows = viewModel.getShows().subList(5, viewModel.getShows().size)
        listAdapter.submitList(shows)
        binding.rvPopularShows.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }
}