package com.homindolentrahar.misbar.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.others.constants.FavoriteItemType
import com.homindolentrahar.misbar.others.wrappers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

data class NavigationData(
    val itemId: Int,
    val type: FavoriteItemType
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _favoritesData = MutableLiveData<Resource<PagingData<FavoritesModel>>>()
    val favoritesData: LiveData<Resource<PagingData<FavoritesModel>>>
        get() = _favoritesData

    private val _detailNavigationData = MutableLiveData<NavigationData?>()
    val detailNavigationData: LiveData<NavigationData?>
        get() = _detailNavigationData

    fun navigateToDetail(itemId: Int, type: FavoriteItemType) {
        _detailNavigationData.value = NavigationData(itemId, type)
    }

    fun clearNavigateToDetail() {
        _detailNavigationData.value = null
    }

    @ExperimentalCoroutinesApi
    fun getFavoritesData() {
        _favoritesData.value = Resource.Loading()
        repository.getAllFavoriteItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _favoritesData.value = Resource.Success(items)
                },
                { throwable ->
                    _favoritesData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    @ExperimentalCoroutinesApi
    fun getFavoritesDataByType(type: String) {
        _favoritesData.value = Resource.Loading()
        repository.getAllFavoriteItemsByType(type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items ->
                    _favoritesData.value = Resource.Success(items)
                },
                { throwable ->
                    _favoritesData.value = Resource.Error(throwable.message.toString())
                },
            )
    }

}