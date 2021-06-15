package com.homindolentrahar.misbar.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import com.homindolentrahar.misbar.others.wrappers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    private val _searchFragmentData = MutableLiveData<Resource<PagingData<SearchModel>>>()
    val searchFragmentData: LiveData<Resource<PagingData<SearchModel>>>
        get() = _searchFragmentData

    private val _detailNavigationData = MutableLiveData<SearchModel?>()
    val detailNavigationData: LiveData<SearchModel?>
        get() = _detailNavigationData

    fun navigateToDetail(model: SearchModel) {
        _detailNavigationData.value = model
    }

    fun clearNavigateToDetail() {
        _detailNavigationData.value = null
    }

    fun clearSearch() {
        _searchFragmentData.value = Resource.Success(PagingData.empty())
    }

    @ExperimentalCoroutinesApi
    fun getSearchFragmentData(query: String) {
        _searchFragmentData.value = Resource.Loading()
        repository.searchItems(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe(
                { items ->
                    _searchFragmentData.value = Resource.Success(items)
                },
                { throwable ->
                    _searchFragmentData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

//    Test purpose only
    fun getSearchFragmentDataTest(query: String) {
        _searchFragmentData.value = Resource.Loading()
        repository.searchItems(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _searchFragmentData.value = Resource.Success(items)
                },
                { throwable ->
                    _searchFragmentData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

}