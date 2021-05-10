package com.homindolentrahar.misbar.data.movies

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LocalMoviesRepositoryTest {

    private lateinit var repository: LocalMoviesRepository

    private val dummyMovieId = 1

    @Before
    fun setup() {
        repository = LocalMoviesRepository()
    }

    @Test
    fun assertMoviesNotEmpty() {
        val movies = repository.getItemsFromLocalSource()

        assertEquals(19, movies.size)
    }

    @Test
    fun assertSpecificMovieNotNull() {
        val movie = repository.getSpecificItemFromLocalSource(dummyMovieId)

        assertNotNull(movie)
    }

}