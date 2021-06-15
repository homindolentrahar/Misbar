package com.homindolentrahar.misbar.ui.shows

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
import com.homindolentrahar.misbar.data.repositories.FakeShowsRepositoryAndroid
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import com.homindolentrahar.misbar.utils.mappers.FormatMapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class DetailShowsFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()

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
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailShowsModel.id
        )

        launchFragmentInHiltContainer<DetailShowsFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.back_button)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.back_button)).perform(click())
    }

    @Test
    fun testLoadCorrespondingData() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailShowsModel.id
        )

        launchFragmentInHiltContainer<DetailShowsFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.genres_chip)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.genre_item_chip), isDisplayed()))
        onView(withId(R.id.rating_chip)).check(matches(withText(dummyDetailShowsModel.voteAverage.toString())))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyDetailShowsModel.name)))
        onView(withId(R.id.tv_airing)).check(
            matches(
                withText(
                    FormatMapper.formatReleaseDate(
                        dummyDetailShowsModel.firstAirDate
                    )
                )
            )
        )
        onView(withId(R.id.tv_duration)).check(
            matches(
                withText(
                    FormatMapper.formatRuntime(dummyDetailShowsModel.episodeRunTime[0])
                )
            )
        )
        onView(withId(R.id.tv_creator)).check(
            matches(
                withText(
                    dummyDetailShowsModel.createdBy.joinToString(", ")
                )
            )
        )
        onView(withId(R.id.tv_language)).check(
            matches(
                withText(
                    dummyDetailShowsModel.spokenLanguages.joinToString(
                        ", "
                    )
                )
            )
        )
        onView(withId(R.id.tv_episodes)).check(
            matches(
                withText(
                    FormatMapper.formatRevenueString(
                        dummyDetailShowsModel.numberOfEpisodes
                    )
                )
            )
        )
        onView(withId(R.id.tv_production)).check(
            matches(
                withText(
                    dummyDetailShowsModel.productionCompanies[0].name
                )
            )
        )

        onView(withId(R.id.tv_synopsis)).check(matches(withText(dummyDetailShowsModel.overview)))
        onView(allOf(withId(R.id.season_list_item), isCompletelyDisplayed()))
        onView(withId(R.id.tv_last_name)).check(matches(withText(dummyDetailShowsModel.lastEpisodeToAir.name)))
        onView(withId(R.id.tv_last_episode)).check(matches(withText("Episode ${dummyDetailShowsModel.lastEpisodeToAir.episodeNumber}")))
        onView(withId(R.id.tv_last_season)).check(matches(withText("Season ${dummyDetailShowsModel.lastEpisodeToAir.seasonNumber}")))
        onView(withId(R.id.tv_last_airing)).check(
            matches(
                withText(
                    FormatMapper.formatReleaseDate(
                        dummyDetailShowsModel.lastEpisodeToAir.airDate
                    )
                )
            )
        )

        onView(withId(R.id.tv_next_name)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_next_episode)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_next_season)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_next_airing)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testShareButton() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel =
            ShowsViewModel(FakeShowsRepositoryAndroid(), FakeFavoritesRepositoryAndroid())
        val bundle = bundleOf(
            "id" to dummyDetailShowsModel.id
        )

        launchFragmentInHiltContainer<DetailShowsFragment>(
            fragmentFactory = fragmentFactory,
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
            showsViewModel = testViewModel
        }

        onView(withId(R.id.share_button)).perform(click())
    }

}