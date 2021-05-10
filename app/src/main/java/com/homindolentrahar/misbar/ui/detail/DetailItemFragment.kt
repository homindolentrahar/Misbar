package com.homindolentrahar.misbar.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.homindolentrahar.misbar.databinding.FragmentDetailItemBinding
import com.homindolentrahar.misbar.domain.core.ItemModel
import com.homindolentrahar.misbar.ui.movies.MoviesViewModel
import com.homindolentrahar.misbar.ui.shows.ShowsViewModel

enum class DetailItemType(val type: String) {
    Movies("movies"),
    Shows("shows")
}

class DetailItemFragment : Fragment() {

    private lateinit var binding: FragmentDetailItemBinding
    private lateinit var viewModel: ViewModel
    private val args: DetailItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Get itemId and type
        val id = args.itemId
        val type = args.type
//        Init viewModel
        val viewModelClass =
            if (type == DetailItemType.Movies.type) MoviesViewModel::class.java
            else ShowsViewModel::class.java
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[viewModelClass]
//        Populate item
        val item =
            if (type == DetailItemType.Movies.type) (viewModel as MoviesViewModel).getDetailMovie(
                id
            )
            else (viewModel as ShowsViewModel).getDetailShow(id)
        populateItem(item)
//        Handle buttons
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.shareButton.setOnClickListener {
            shareTitle(item)
        }
    }

    private fun populateItem(item: ItemModel) {
        binding.item = item
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun shareTitle(item: ItemModel) {
        val shareIntent = Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, item.title + "\n\n" + item.synopsis)
                type = "text/plain"
            }, "Share Item"
        )
        startActivity(shareIntent)
    }

}