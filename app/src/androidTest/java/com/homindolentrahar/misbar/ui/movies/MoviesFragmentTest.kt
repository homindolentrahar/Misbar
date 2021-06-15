package com.homindolentrahar.misbar.ui.movies

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
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.repositories.FakeMoviesRepositoryAndroid
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.others.constants.Constants
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.constants.ItemType
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
import com.homindolentrahar.misbar.ui.genres.GenresFragment
import com.homindolentrahar.misbar.ui.genres.GenresFragmentType
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.getOrAwaitValueTestAndroid
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MoviesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    @Inject
    @Named("favorites_repository")
    lateinit var favoritesRepository: FavoritesRepository

    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()
    private val dummyRandomGenre = Constants.getLocalGenres(ItemType.Movies).random()

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        hiltRule.inject()
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testCarouselSwipe() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = MoviesViewModel(FakeMoviesRepositoryAndroid(), favoritesRepository)

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.movies_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.movies_carousel)).perform(
            swipeLeft(), swipeLeft(), swipeLeft(), swipeLeft()
        )
        onView(withId(R.id.movies_carousel)).perform(
            swipeRight(), swipeRight(), swipeRight(), swipeRight()
        )
    }

    @Test
    fun testListScroll() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = MoviesViewModel(FakeMoviesRepositoryAndroid(), favoritesRepository)

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.movies_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                4
            )
        )
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }

    @Test
    fun testCarouselSwipeToPosition_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = MoviesViewModel(FakeMoviesRepositoryAndroid(), favoritesRepository)

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.movies_carousel)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.carousel_item), isDisplayed())).perform(
            swipeLeft(), swipeLeft(), swipeLeft(), click()
        )

//        Navigating to DetailMoviesFragment
        val bundle = bundleOf(
            "id" to dummyDetailMoviesModel.id,
        )
        launchFragmentInHiltContainer<DetailMoviesFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun testListScrollToPosition_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = MoviesViewModel(FakeMoviesRepositoryAndroid(), favoritesRepository)

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.rv_popular_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        assertThat(testViewModel.detailNavigationData.getOrAwaitValueTestAndroid()).isNotEqualTo(-1)
//       Navigating to DetailMovies
        val bundle = bundleOf(
            "id" to dummyDetailMoviesModel.id,
        )
        launchFragmentInHiltContainer<DetailMoviesFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun testGenreBanner_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = MoviesViewModel(FakeMoviesRepositoryAndroid(), favoritesRepository)

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.genre_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_check)).perform(click())

        assertThat(testViewModel.genreNavigationData.getOrAwaitValueTestAndroid()).isNotNull()

        val bundle = bundleOf(
            "type" to GenresFragmentType.Movies.name,
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