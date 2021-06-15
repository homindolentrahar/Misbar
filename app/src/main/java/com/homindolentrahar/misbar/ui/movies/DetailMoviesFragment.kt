package com.homindolentrahar.misbar.ui.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.databinding.FragmentDetailMoviesBinding
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.animation.AnimationUtils
import com.homindolentrahar.misbar.utils.helpers.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMoviesFragment : Fragment() {

    private lateinit var binding: FragmentDetailMoviesBinding
    private var isSynopsisExpanded = false
    private lateinit var args: Bundle
    lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
//        Get itemId and type
        args = requireArguments()
        val id = args.getInt("id")
//        Get data
        getData(id)
    }

    private fun getData(moviesId: Int) {
//        Observing data
//        DetailMoviesData
        EspressoIdlingResource.increment()
        moviesViewModel.getMoviesDetail(moviesId)
        moviesViewModel.detailMoviesData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingAnimation.loading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    populateData(resource.data)

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
//        Favorite
        EspressoIdlingResource.increment()
        moviesViewModel.getSingleFavoriteMovie(moviesId)
        moviesViewModel.singleFavoriteData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Error -> {
                    binding.favoriteButton.setImageResource(R.drawable.ic_love)
                }
                is Resource.Success -> {
                    binding.favoriteButton.setImageResource(R.drawable.ic_loved)
                }
                else -> {
                }
            }

            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        })
//        Message
        moviesViewModel.actionMessageData.observe(viewLifecycleOwner, { message ->
            if (message.isNotEmpty()) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

                moviesViewModel.clearActionMessage()
                moviesViewModel.getSingleFavoriteMovie(moviesId)

                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            }
        })
    }

    private fun populateData(detailData: DetailMoviesModel?) {
        detailData?.let { data ->
            bindImagePoster(binding.imgPoster, data.backdropPath)
            bindChips(binding.genresChip, data.genres, data.voteAverage)
            binding.tvTitle.text = data.title
            bindReleaseDate(binding.tvRelease, data.releaseDate)
            bindRuntime(binding.tvDuration, data.runtime)
            bindProductionLocation(binding.tvProductionLocation, data.productionCountries)
            binding.tvLanguage.text = data.spokenLanguages.joinToString(", ")
            if (data.revenue != 0) {
                bindRevenue(binding.tvRevenue, data.revenue)
            } else {
                binding.detailInfoRevenue.visibility = View.GONE
            }
            binding.tvProduction.text = data.productionCompanies[0].name
            binding.expandableSynopsis.tvSynopsis.text = data.overview
            binding.expandableSynopsis.root.setOnClickListener {
                isSynopsisExpanded = if (isSynopsisExpanded) {
                    AnimationUtils.collapse(binding.expandableSynopsis.root)
                    false
                } else {
                    AnimationUtils.expand(binding.expandableSynopsis.root)
                    true
                }
            }
//        Handle buttons
            binding.shareButton.setOnClickListener {
                shareTitle(data)
            }
            binding.favoriteButton.setOnClickListener {
                moviesViewModel.toggleFavorite(data)
            }
            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun shareTitle(item: DetailMoviesModel) {
        val shareIntent = Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, item.title + "\n\n" + item.overview)
                type = "text/plain"
            }, "Share Item"
        )
        startActivity(shareIntent)
    }

}