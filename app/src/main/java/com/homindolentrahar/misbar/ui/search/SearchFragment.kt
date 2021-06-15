package com.homindolentrahar.misbar.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.homindolentrahar.misbar.databinding.FragmentSearchBinding
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var listAdapter: SearchListItemAdapter
    lateinit var viewModel: SearchViewModel

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Init ViewModel
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
//        Init list
        listAdapter = SearchListItemAdapter { model ->
            viewModel.navigateToDetail(model)
        }
        binding.rvSearch.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
        binding.searchField.imeOptions = EditorInfo.IME_ACTION_SEARCH
//        Search bar
        val observableSearch = Observable.create<String> { emitter ->
            binding.searchField.addTextChangedListener { editable ->
                val query = editable.toString().trim()
                if (query.length >= 3) {
                    emitter.onNext(query)
                } else {
                    viewModel.clearSearch()
                }
            }
        }
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
//        Observe search query change
        disposable.add(
            observableSearch
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { query ->
                        EspressoIdlingResource.increment()
                        viewModel.getSearchFragmentData(query)
                    },
                    { error ->
                        Log.d(SearchFragment::class.java.simpleName, "Error : ${error.message}")
                    }
                )
        )
//        Observe data
//        EspressoIdlingResource.increment()
        viewModel.searchFragmentData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
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
                is Resource.Loading -> {
                    binding.loadingAnimation.loading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingAnimation.loading.visibility = View.GONE

                    resource.data?.let { data ->
                        listAdapter.submitData(lifecycle, data)
                    }

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            }
        })
//        Observe navigation
        viewModel.detailNavigationData.observe(viewLifecycleOwner, { model ->
            if (model != null) {
                val action =
                    if (model.mediaType == "movie")
                        SearchFragmentDirections.actionSearchFragmentToDetailMoviesFragment(model.id)
                    else
                        SearchFragmentDirections.actionSearchFragmentToDetailShowsFragment(model.id)
                findNavController().navigate(action)
//                Clear Navigation
                viewModel.clearNavigateToDetail()
            }
        })
//        Button Interactions
        binding.backButton.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }
}