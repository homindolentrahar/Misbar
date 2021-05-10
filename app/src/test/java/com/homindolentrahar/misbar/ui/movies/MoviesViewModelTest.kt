package com.homindolentrahar.misbar.ui.movies

import com.homindolentrahar.misbar.data.core.ItemLocalEntity
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.data.movies.LocalMoviesRepository
import com.homindolentrahar.misbar.domain.core.ItemModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var mockRepository: LocalMoviesRepository

    private val dummyMovieId = 1
    private val dummyMoviesModel: List<ItemModel> =
        LocalItems.localMovies.map { entity -> entity.toModel() }
    private val dummyMovieModel: ItemModel =
        dummyMoviesModel.first { model -> model.id == dummyMovieId }
    private val dummyEmptyMovieModel: ItemModel = ItemModel.empty()
    private val dummyMovieEntity: ItemLocalEntity =
        LocalItems.localMovies.first { entity -> entity.id == dummyMovieId }

    @Before
    fun setup() {
        viewModel = MoviesViewModel()
        viewModel.repository = mockRepository
    }

    @Test
    fun assertMoviesNotEmpty() {
        `when`(mockRepository.getItemsFromLocalSource()).thenReturn(emptyList())

        val movies = viewModel.getMovies()

        verify(mockRepository).getItemsFromLocalSource()
        assertNotEquals(dummyMoviesModel.size, movies.size)
    }

    @Test
    fun assertMoviesMapped() {
        `when`(mockRepository.getItemsFromLocalSource()).thenReturn(LocalItems.localMovies)

        val movies = viewModel.getMovies()

        verify(mockRepository).getItemsFromLocalSource()
        assertEquals(dummyMoviesModel, movies)
    }

    @Test
    fun assertDetailMovieMapped() {
        `when`(mockRepository.getSpecificItemFromLocalSource(anyInt())).thenReturn(
            dummyMovieEntity
        )

        val movie = viewModel.getDetailMovie(dummyMovieId)

        verify(mockRepository).getSpecificItemFromLocalSource(dummyMovieId)
        assertEquals(dummyMovieModel, movie)
    }

    @Test
    fun assertNullReturnEmptyData() {
        `when`(mockRepository.getSpecificItemFromLocalSource(anyInt())).thenReturn(null)

        val model = viewModel.getDetailMovie(dummyMovieId)

        verify(mockRepository).getSpecificItemFromLocalSource(dummyMovieId)
        assertEquals(dummyEmptyMovieModel, model)
        assertNotEquals(dummyMovieModel, model)
    }
}