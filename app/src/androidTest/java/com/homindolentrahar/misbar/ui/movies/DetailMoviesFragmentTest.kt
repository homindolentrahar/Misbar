package com.homindolentrahar.misbar.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.repositories.FakeFavoritesRepositoryAndroid
import com.homindolentrahar.misbar.data.repositories.FakeMoviesRepositoryAndroid
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.mappers.FormatMapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class DetailMoviesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()

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
    fun testBackButton() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailMoviesModel.id
        )

        launchFragmentInHiltContainer<DetailMoviesFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.back_button)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.back_button)).perform(click())
    }

    @Test
    fun testLoadCorrespondingData() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailMoviesModel.id
        )

        launchFragmentInHiltContainer<DetailMoviesFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle,
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.genres_chip)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.genre_item_chip), isCompletelyDisplayed()))
        onView(withId(R.id.rating_chip)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyDetailMoviesModel.title)))
        onView(withId(R.id.tv_release)).check(
            matches(
                withText(
                    FormatMapper.formatReleaseDate(
                        dummyDetailMoviesModel.releaseDate
                    )
                )
            )
        )
        onView(withId(R.id.tv_duration)).check(
            matches(
                withText(
                    FormatMapper.formatRuntime(
                        dummyDetailMoviesModel.runtime
                    )
                )
            )
        )
        onView(withId(R.id.tv_production_location)).check(matches(withText(dummyDetailMoviesModel.productionCountries[0])))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_revenue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_production)).check(matches(withText(dummyDetailMoviesModel.productionCompanies[0].name)))
        onView(withId(R.id.tv_synopsis)).check(matches(withText(dummyDetailMoviesModel.overview)))
    }

    @Test
    fun testShareButton() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            MoviesViewModel(FakeMoviesRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailMoviesModel.id
        )

        launchFragmentInHiltContainer<DetailMoviesFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            moviesViewModel = testViewModel
        }

        onView(withId(R.id.share_button)).perform(click())
    }
}