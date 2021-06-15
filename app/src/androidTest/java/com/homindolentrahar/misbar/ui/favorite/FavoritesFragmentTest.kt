package com.homindolentrahar.misbar.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.repositories.FakeFavoritesRepositoryAndroid
import com.homindolentrahar.misbar.launchFragmentInHiltContainer
import com.homindolentrahar.misbar.ui.core.AppFragmentFactory
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
class FavoritesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: AppFragmentFactory

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
    fun testChipType() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = FavoritesViewModel(FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<FavoritesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.favorites_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.filters_chip))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.filters_movies_chip))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.filters_shows_chip))
            .perform(click())
    }

    @Test
    fun testListScroll() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = FavoritesViewModel(FakeFavoritesRepositoryAndroid())

        launchFragmentInHiltContainer<FavoritesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.favorites_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_favorites)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                4
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.rv_favorites)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }
}