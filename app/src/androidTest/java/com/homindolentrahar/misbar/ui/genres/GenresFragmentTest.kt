package com.homindolentrahar.misbar.ui.genres

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.repositories.FakeFavoritesRepositoryAndroid
import com.homindolentrahar.misbar.data.repositories.FakeMoviesRepositoryAndroid
import com.homindolentrahar.misbar.data.repositories.FakeShowsRepositoryAndroid
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.others.constants.Constants
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.constants.ItemType
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
import com.homindolentrahar.misbar.ui.core.withDrawable
import com.homindolentrahar.misbar.ui.movies.MoviesViewModel
import com.homindolentrahar.misbar.ui.shows.ShowsViewModel
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class GenresFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

//    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()
//    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()
    private val dummyMoviesRandomGenre = Constants.getLocalGenres(ItemType.Movies)[1]
    private val dummyShowsRandomGenre = Constants.getLocalGenres(ItemType.Shows)[2]

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
    fun testGenreBanner_movies() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "type" to GenresFragmentType.Movies.name,
            "genre" to dummyMoviesRandomGenre,
        )

        launchFragmentInHiltContainer<GenresFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.genre_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name)).check(matches(withText(dummyMoviesRandomGenre.name)))
        onView(withId(R.id.tv_description)).check(matches(withText(dummyMoviesRandomGenre.description)))
        onView(withId(R.id.img_backdrop)).check(matches(withDrawable(dummyMoviesRandomGenre.imageRes)))
        onView(withId(R.id.rv_genres)).check(matches(isDisplayed()))
    }

    @Test
    fun testListScroll_movies() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "type" to GenresFragmentType.Movies.name,
            "genre" to dummyMoviesRandomGenre,
        )

        launchFragmentInHiltContainer<GenresFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.rv_genres)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                9
            ), RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }

    @Test
    fun testGenreBanner_shows() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "type" to GenresFragmentType.Shows.name,
            "genre" to dummyShowsRandomGenre,
        )

        launchFragmentInHiltContainer<GenresFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.genre_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name)).check(matches(withText(dummyShowsRandomGenre.name)))
        onView(withId(R.id.tv_description)).check(matches(withText(dummyShowsRandomGenre.description)))
        onView(withId(R.id.img_backdrop)).check(matches(withDrawable(dummyShowsRandomGenre.imageRes)))
        onView(withId(R.id.rv_genres)).check(matches(isDisplayed()))
    }

    @Test
    fun testListScroll_shows() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "type" to GenresFragmentType.Shows.name,
            "genre" to dummyShowsRandomGenre,
        )

        launchFragmentInHiltContainer<GenresFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.rv_genres)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                9
            ), RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }

//    @Test
//    fun testListScrollToPosition_shows_click() {
//        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
//        val testViewModel =
//            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
//        val bundle = bundleOf(
//            "type" to GenresFragmentType.Shows.name,
//            "genre" to dummyShowsRandomGenre,
//        )
//
//        launchFragmentInHiltContainer<GenresFragment>(
//            fragmentFactory = fragmentFactory,
//            fragmentArgs = bundle
//        ) {
//            Navigation.setViewNavController(requireView(), navController)
//            showsViewModel = testViewModel
//        }
//
//        onView(withId(R.id.rv_genres)).check(matches(isDisplayed())).perform(
//            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
//                0
//            ),
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                0, click()
//            )
//        )
//
//        val detailBundle = bundleOf(
//            "id" to dummyDetailShowsModel.id,
//        )
//
//        launchFragmentInHiltContainer<DetailShowsFragment>(
//            fragmentFactory = fragmentFactory,
//            fragmentArgs = detailBundle
//        ) {
//            Navigation.setViewNavController(requireView(), navController)
//            showsViewModel = testViewModel
//        }
//
//        assertThat(testViewModel.detailNavigationData.getOrAwaitValueTestAndroid()).isNotEqualTo(-1)
//
//        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
//    }

}