package com.homindolentrahar.misbar.ui.shows

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
import com.homindolentrahar.misbar.databinding.FragmentDetailShowsBinding
import com.homindolentrahar.misbar.domain.models.DetailShowsModel
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.animation.AnimationUtils
import com.homindolentrahar.misbar.utils.helpers.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailShowsFragment : Fragment() {

    private lateinit var binding: FragmentDetailShowsBinding
    private lateinit var args: Bundle
    private var isSynopsisExpanded = false
    private var isSeasonsExpanded = false
    private var isEpisodesExpanded = false

    lateinit var showsViewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        showsViewModel = ViewModelProvider(this).get(ShowsViewModel::class.java)
//        Get itemId and type
        args = requireArguments()
        val id = args.getInt("id")
//        Get data
        getData(id)
    }

    private fun getData(showsId: Int) {
//        Observing data
//        DetailShowsData
        EspressoIdlingResource.increment()
        showsViewModel.getShowsDetail(showsId)
        showsViewModel.detailShowsData.observe(viewLifecycleOwner, { resource ->
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
        showsViewModel.getSingleFavoriteMovie(showsId)
        showsViewModel.singleFavoriteData.observe(viewLifecycleOwner, { resource ->
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
        showsViewModel.actionMessageData.observe(viewLifecycleOwner, { message ->
            if (message.isNotEmpty()) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

                showsViewModel.clearActionMessage()
                showsViewModel.getSingleFavoriteMovie(showsId)

                if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            }
        })
    }

    private fun populateData(detailData: DetailShowsModel?) {
        detailData?.let { data ->
            bindImagePoster(binding.imgPoster, data.backdropPath)
            bindChips(binding.genresChip, data.genres, data.voteAverage)
            binding.tvTitle.text = data.name
            bindReleaseDate(binding.tvAiring, data.firstAirDate)
            bindRuntime(binding.tvDuration, data.episodeRunTime[0])
            if (data.createdBy.isNotEmpty()) {
                bindCreator(binding.tvCreator, data.createdBy)
            }
            binding.tvLanguage.text = data.spokenLanguages.joinToString(", ")
            binding.tvEpisodes.text = data.numberOfEpisodes.toString()
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
            val seasonsAdapter = SeasonsListItemAdapter()
            seasonsAdapter.submitList(data.seasons)
            binding.expandableSeasons.rvSeasons.apply {
                adapter = seasonsAdapter
                setHasFixedSize(true)
            }
            binding.expandableSeasons.root.setOnClickListener {
                isSeasonsExpanded = if (isSeasonsExpanded) {
                    AnimationUtils.collapse(binding.expandableSeasons.root)
                    false
                } else {
                    AnimationUtils.expand(binding.expandableSeasons.root)
                    true
                }
            }

            val lastEpisode = data.lastEpisodeToAir
            val nextEpisode = data.nextEpisodeToAir
            binding.expandableEpisodes.root.setOnClickListener {
                isEpisodesExpanded = if (isEpisodesExpanded) {
                    AnimationUtils.collapse(binding.expandableEpisodes.root)
                    false
                } else {
                    AnimationUtils.expand(binding.expandableEpisodes.root)
                    true
                }
            }

            bindImagePoster(binding.expandableEpisodes.imgLastPoster, lastEpisode.stillPath)
            binding.expandableEpisodes.tvLastName.text = lastEpisode.name
            "Episode ${lastEpisode.episodeNumber}".also {
                binding.expandableEpisodes.tvLastEpisode.text = it
            }
            bindReleaseDate(binding.expandableEpisodes.tvLastAiring, lastEpisode.airDate)
            "Season ${lastEpisode.seasonNumber}".also {
                binding.expandableEpisodes.tvLastSeason.text = it
            }

            if (nextEpisode.episodeNumber > 0) {
                bindImagePoster(binding.expandableEpisodes.imgNextPoster, nextEpisode.stillPath)
                binding.expandableEpisodes.tvNextName.text = nextEpisode.name
                "Episode ${nextEpisode.episodeNumber}".also {
                    binding.expandableEpisodes.tvNextEpisode.text = it
                }
                bindReleaseDate(binding.expandableEpisodes.tvNextAiring, nextEpisode.airDate)
                "Season ${nextEpisode.seasonNumber}".also {
                    binding.expandableEpisodes.tvNextSeason.text = it
                }

                binding.expandableEpisodes.nextEpisodeContainer.visibility = View.VISIBLE
            } else {
                binding.expandableEpisodes.nextEpisodeContainer.visibility = View.GONE
            }
//        Handle buttons
            binding.shareButton.setOnClickListener {
                shareTitle(data)
            }
            binding.favoriteButton.setOnClickListener {
                EspressoIdlingResource.increment()
                showsViewModel.toggleFavorite(data)
            }
            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun shareTitle(item: DetailShowsModel) {
        val shareIntent = Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, item.name + "\n\n" + item.overview)
                type = "text/plain"
            }, "Share Item"
        )
        startActivity(shareIntent)
    }
}