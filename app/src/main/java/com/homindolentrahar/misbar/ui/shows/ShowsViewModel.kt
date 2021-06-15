package com.homindolentrahar.misbar.ui.shows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homindolentrahar.misbar.domain.models.*
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import com.homindolentrahar.misbar.others.constants.Constants
import com.homindolentrahar.misbar.others.constants.ItemType
import com.homindolentrahar.misbar.others.wrappers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val repository: ShowsRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _showsFragmentData = MutableLiveData<Resource<ShowsFragmentData>>()
    val showsFragmentData: LiveData<Resource<ShowsFragmentData>>
        get() = _showsFragmentData

    private val _showsByGenre = MutableLiveData<Resource<ShowsFragmentData>>()
    val showsByGenre: LiveData<Resource<ShowsFragmentData>>
        get() = _showsByGenre

    private val _detailShowsData = MutableLiveData<Resource<DetailShowsModel>>()
    val detailShowsData: LiveData<Resource<DetailShowsModel>>
        get() = _detailShowsData

    private val _singleFavoriteData = MutableLiveData<Resource<FavoritesModel>>()
    val singleFavoriteData: LiveData<Resource<FavoritesModel>>
        get() = _singleFavoriteData

    private val _actionMessageData = MutableLiveData<String>()
    val actionMessageData: LiveData<String>
        get() = _actionMessageData

//    Navigation

    private val _detailNavigationData = MutableLiveData<Int>()
    val detailNavigationData: LiveData<Int>
        get() = _detailNavigationData

    private val _genreNavigationData = MutableLiveData<LocalGenresModel?>()
    val genreNavigationData: LiveData<LocalGenresModel?>
        get() = _genreNavigationData

    val randomGenres: LocalGenresModel = Constants.getLocalGenres(ItemType.Shows).random()

    fun navigateToDetail(showsId: Int) {
        _detailNavigationData.value = showsId
    }

    fun clearNavigateToDetail() {
        _detailNavigationData.value = -1
    }

    fun navigateToGenreFragment(item: LocalGenresModel) {
        _genreNavigationData.value = item
    }

    fun clearNavigateToGenreFragment() {
        _genreNavigationData.value = null
    }

    fun getShowsFragmentData() {
        _showsFragmentData.value = Resource.Loading()
        Observable.zip(
            repository.getTodayAiring(),
            repository.getPopularShows(),
            { now, popular ->
                ShowsFragmentData(now.take(5), popular)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { shows ->
                    _showsFragmentData.value = Resource.Success(shows)
                },
                { throwable ->
                    _showsFragmentData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    fun getShowsByGenre(genresId: Int) {
        _showsByGenre.value = Resource.Loading()
        repository.getShowsByGenre(genresId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { ShowsFragmentData(pagedListData = it) }
            .subscribe(
                { shows ->
                    _showsByGenre.value = Resource.Success(shows)
                },
                { throwable ->
                    _showsByGenre.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    fun getShowsDetail(showsId: Int) {
        _detailShowsData.value = Resource.Loading()
        repository.getShowsDetail(showsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { shows ->
                    _detailShowsData.value = Resource.Success(shows)
                },
                { throwable ->
                    _detailShowsData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    fun getSingleFavoriteMovie(moviesId: Int) {
        favoritesRepository.getSingleFavoriteItem(moviesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    _singleFavoriteData.value = Resource.Success(data)
                },
                { throwable ->
                    _singleFavoriteData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    private fun addFavoriteMovie(model: DetailShowsModel) {
        favoritesRepository.addFavorite(FavoritesModel.fromDetailShowsModel(model))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _actionMessageData.value = "Nice taste!"
                },
                {
                    _actionMessageData.value =
                        "Sorry, for some reason we cannot add your favorite movie :("
                    Log.d(ShowsViewModel::class.java.simpleName, it.message.toString())
                }
            )
    }

    private fun deleteFavoriteMovie(model: DetailShowsModel) {
        favoritesRepository.deleteFavorite(FavoritesModel.fromDetailShowsModel(model))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _actionMessageData.value = "Don\'t worry, everybody have their own taste tho"
                }, {
                    _actionMessageData.value =
                        "Sorry, for some reason we cannot delete your favorite movie :("
                    Log.d(ShowsViewModel::class.java.simpleName, it.message.toString())
                }
            )
    }

    private fun checkIsFavorite(): Boolean = _singleFavoriteData.value?.data != null

    fun toggleFavorite(model: DetailShowsModel) {
        if (checkIsFavorite()) {
            deleteFavoriteMovie(model)
        } else {
            addFavoriteMovie(model)
        }
    }

    fun clearActionMessage() {
        _actionMessageData.value = ""
    }
}