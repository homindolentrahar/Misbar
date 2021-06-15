package com.homindolentrahar.misbar.ui.movies

import android.util.Log
import androidx.lifecycle.*
import com.homindolentrahar.misbar.domain.models.*
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
import com.homindolentrahar.misbar.others.constants.Constants
import com.homindolentrahar.misbar.others.constants.ItemType
import com.homindolentrahar.misbar.others.wrappers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val _moviesFragmentData = MutableLiveData<Resource<MoviesFragmentData>>()
    val moviesFragmentData: LiveData<Resource<MoviesFragmentData>>
        get() = _moviesFragmentData

    private val _moviesByGenre = MutableLiveData<Resource<MoviesFragmentData>>()
    val moviesByGenre: LiveData<Resource<MoviesFragmentData>>
        get() = _moviesByGenre

    private val _moviesSearchData = MutableLiveData<Resource<MoviesFragmentData>>()
    val moviesSearchData: LiveData<Resource<MoviesFragmentData>>
        get() = _moviesSearchData

    private val _detailMoviesData = MutableLiveData<Resource<DetailMoviesModel>>()
    val detailMoviesData: LiveData<Resource<DetailMoviesModel>>
        get() = _detailMoviesData

    private val _singleFavoriteData = MutableLiveData<Resource<FavoritesModel>>()
    val singleFavoriteData: LiveData<Resource<FavoritesModel>>
        get() = _singleFavoriteData

    private val _actionMessageData = MutableLiveData<String>()
    val actionMessageData: LiveData<String>
        get() = _actionMessageData

//    Navigation

    private val _detailNavigationData = MutableLiveData<Int>(-1)
    val detailNavigationData: LiveData<Int>
        get() = _detailNavigationData

    private val _genreNavigationData = MutableLiveData<LocalGenresModel?>()
    val genreNavigationData: LiveData<LocalGenresModel?>
        get() = _genreNavigationData

    val randomGenres: LocalGenresModel = Constants.getLocalGenres(ItemType.Movies).random()

    fun navigateToDetail(moviesId: Int) {
        _detailNavigationData.value = moviesId
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

    fun getMoviesFragmentData() {
        _moviesFragmentData.value = Resource.Loading()
        Observable.zip(
            repository.getNowPlaying(),
            repository.getPopularMovies(),
            { now, popular -> MoviesFragmentData(now.take(5), popular) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movies ->
                    _moviesFragmentData.value = Resource.Success(movies)
                },
                { throwable ->
                    _moviesFragmentData.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    fun getMoviesByGenre(genresId: Int) {
        _moviesByGenre.value = Resource.Loading()
        repository.getMoviesByGenre(genresId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { MoviesFragmentData(pagedListData = it) }
            .subscribe(
                { movies ->
                    _moviesByGenre.value = Resource.Success(movies)
                },
                { throwable ->
                    _moviesByGenre.value = Resource.Error(throwable.message.toString())
                }
            )
    }

    fun getMoviesDetail(moviesId: Int) {
        _detailMoviesData.value = Resource.Loading()
        repository.getMoviesDetail(moviesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { movie ->
                    _detailMoviesData.value = Resource.Success(movie)
                },
                { throwable ->
                    _detailMoviesData.value = Resource.Error(throwable.message.toString())
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

    fun addFavoriteMovie(model: DetailMoviesModel) {
        favoritesRepository.addFavorite(FavoritesModel.fromDetailMoviesModel(model))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _actionMessageData.value = "Nice taste!"
                },
                {
                    _actionMessageData.value =
                        "Sorry, for some reason we cannot add your favorite movie :("
                    Log.d(MoviesViewModel::class.java.simpleName, it.message.toString())
                }
            )
    }

    fun deleteFavoriteMovie(model: DetailMoviesModel) {
        favoritesRepository.deleteFavorite(FavoritesModel.fromDetailMoviesModel(model))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _actionMessageData.value = "Don\'t worry, everybody have their own taste tho"
                }, {
                    _actionMessageData.value =
                        "Sorry, for some reason we cannot delete your favorite movie :("
                    Log.d(MoviesViewModel::class.java.simpleName, it.message.toString())
                }
            )
    }

    private fun checkIsFavorite(): Boolean = _singleFavoriteData.value?.data != null

    fun toggleFavorite(model: DetailMoviesModel) {
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

