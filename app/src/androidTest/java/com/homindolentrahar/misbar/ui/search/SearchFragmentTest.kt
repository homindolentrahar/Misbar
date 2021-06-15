package com.homindolentrahar.misbar.ui.search

import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.repositories.FakeSearchRepositoryAndroid
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
class SearchFragmentTest {

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
    fun typeSearchQuery() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = SearchViewModel(FakeSearchRepositoryAndroid())

        launchFragmentInHiltContainer<SearchFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.search_field))
            .check(matches(isDisplayed()))
        onView(withId(R.id.search_field))
            .perform(typeText("Batman"))
        onView(withId(R.id.search_field))
            .perform(clearText())
    }

    @Test
    fun testListScroll() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val testViewModel = SearchViewModel(FakeSearchRepositoryAndroid())

        launchFragmentInHiltContainer<SearchFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }

        onView(withId(R.id.search_field))
            .check(matches(isDisplayed()))
        onView(withId(R.id.search_field))
            .perform(typeText("Batman"))

        Handler(Looper.getMainLooper()).postDelayed({}, 500)

        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                4
            )
        )
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }
}