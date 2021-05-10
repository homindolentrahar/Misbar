package com.homindolentrahar.misbar.data.shows

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LocalShowsRepositoryTest {

    private lateinit var repository: LocalShowsRepository

    private val dummyShowId = 1

    @Before
    fun setup() {
        repository = LocalShowsRepository()
    }

    @Test
    fun assertShowsNotEmpty() {
        val shows = repository.getItemsFromLocalSource()

        assertEquals(20, shows.size)
    }

    @Test
    fun assertSpecificShowNotNull() {
        val show = repository.getSpecificItemFromLocalSource(dummyShowId)

        assertNotNull(show)
    }
}