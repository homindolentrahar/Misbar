package com.homindolentrahar.misbar.ui.shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.data.repositories.FakeFavoritesRepositoryAndroid
import com.homindolentrahar.misbar.data.repositories.FakeMoviesRepositoryAndroid
import com.homindolentrahar.misbar.data.repositories.FakeShowsRepositoryAndroid
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.others.constants.Constants
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.constants.ItemType
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
import com.homindolentrahar.misbar.ui.genres.GenresFragment
import com.homindolentrahar.misbar.ui.genres.GenresFragmentType
import com.homindolentrahar.misbar.ui.movies.MoviesFragment
import com.homindolentrahar.misbar.ui.movies.MoviesViewModel
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.getOrAwaitValueTestAndroid
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ShowsFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()
    private val dummyRandomGenre = Constants.getLocalGenres(ItemType.Shows)[0]

    @Before
    fun setup() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testCarouselSwipe() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<ShowsFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.shows_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.shows_carousel)).perform(
            swipeLeft(), swipeLeft(), swipeLeft(), swipeLeft()
        )
        onView(withId(R.id.shows_carousel)).perform(
            swipeRight(), swipeRight(), swipeRight(), swipeRight()
        )
    }

    @Test
    fun testListScroll() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<ShowsFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.shows_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_popular_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                4
            )
        )

        onView(withId(R.id.rv_popular_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }

    @Test
    fun testCarouselSwipeToPosition_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<ShowsFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.shows_carousel)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.carousel_item), isDisplayed())).perform(
            swipeLeft(), swipeLeft(), swipeLeft(), click()
        )

        assertThat(testViewModel.detailNavigationData.getOrAwaitValueTestAndroid()).isNotEqualTo(-1)
//       Navigating to DetailItemFragment
        val bundle = bundleOf(
            "id" to dummyDetailShowsModel.id,
        )
        launchFragmentInHiltContainer<DetailShowsFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun testListScrollToPosition_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<ShowsFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.rv_popular_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        assertThat(testViewModel.detailNavigationData.getOrAwaitValueTestAndroid()).isNotEqualTo(-1)
//       Navigating to DetailItemFragment
        val bundle = bundleOf(
            "id" to dummyDetailShowsModel.id,
        )
        launchFragmentInHiltContainer<DetailShowsFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun testGenreBanner_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.genre_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_check)).perform(click())

        assertThat(testViewModel.genreNavigationData.getOrAwaitValueTestAndroid()).isNotNull()

        val bundle = bundleOf(
            "type" to GenresFragmentType.Shows.name,
            "genre" to dummyRandomGenre,
        )
        launchFragmentInHiltContainer<GenresFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.genres_fragment)).check(matches(isDisplayed()))
    }

}