package com.homindolentrahar.misbar.ui.shows

import com.homindolentrahar.misbar.data.core.ItemLocalEntity
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.data.shows.LocalShowsRepository
import com.homindolentrahar.misbar.domain.core.ItemModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsViewModelTest {
    private lateinit var viewModel: ShowsViewModel

    @Mock
    private lateinit var mockRepository: LocalShowsRepository

    private val dummyShowId = 1
    private val dummyShowsModel: List<ItemModel> =
        LocalItems.localShows.map { entity -> entity.toModel() }
    private val dummyShowModel: ItemModel =
        dummyShowsModel.first { model -> model.id == dummyShowId }
    private val dummyEmptyShowModel: ItemModel = ItemModel.empty()
    private val dummyShowEntity: ItemLocalEntity =
        LocalItems.localShows.first { entity -> entity.id == dummyShowId }

    @Before
    fun setup() {
        viewModel = ShowsViewModel()
        viewModel.repository = mockRepository
    }

    @Test
    fun assertShowsNotEmpty() {
        `when`(mockRepository.getItemsFromLocalSource()).thenReturn(emptyList())

        val shows = viewModel.getShows()

        verify(mockRepository).getItemsFromLocalSource()
        assertNotEquals(dummyShowsModel.size, shows.size)
    }

    @Test
    fun assertShowsMapped() {
        `when`(mockRepository.getItemsFromLocalSource()).thenReturn(LocalItems.localShows)

        val mappedShows = viewModel.getShows()

        verify(mockRepository).getItemsFromLocalSource()
        assertEquals(dummyShowsModel, mappedShows)
    }

    @Test
    fun assertDetailShowMapped() {
        `when`(mockRepository.getSpecificItemFromLocalSource(anyInt())).thenReturn(
            dummyShowEntity
        )

        val mappedShow = viewModel.getDetailShow(dummyShowId)

        verify(mockRepository).getSpecificItemFromLocalSource(dummyShowId)
        assertEquals(dummyShowModel, mappedShow)
    }

    @Test
    fun assertNullReturnEmptyData() {
        `when`(mockRepository.getSpecificItemFromLocalSource(anyInt())).thenReturn(null)

        val show = viewModel.getDetailShow(dummyShowId)

        verify(mockRepository).getSpecificItemFromLocalSource(dummyShowId)
        assertEquals(dummyEmptyShowModel, show)
        assertNotEquals(dummyShowModel, show)
    }
}