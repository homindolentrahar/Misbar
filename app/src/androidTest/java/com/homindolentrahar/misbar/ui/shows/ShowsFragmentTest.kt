package com.homindolentrahar.misbar.ui.shows

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.ui.detail.DetailItemFragment
import com.homindolentrahar.misbar.ui.detail.DetailItemType
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import org.hamcrest.CoreMatchers.allOf

@RunWith(AndroidJUnit4::class)
class ShowsFragmentTest {
    @Test
    fun testCarouselSwipe() {
//        Scroll to last position of carousel, then scroll back to first position
        launchFragmentInContainer<ShowsFragment>(themeResId = R.style.Theme_Misbar)

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
//        Scroll to last position of list, then scroll back to first position
        launchFragmentInContainer<ShowsFragment>(themeResId = R.style.Theme_Misbar)

        onView(withId(R.id.shows_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_popular_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                LocalItems.localShows.lastIndex
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
//        Scroll 3 times on carousel, then click it
//        It will navigate to DetailItemFragment with corresponding data
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<ShowsFragment>(themeResId = R.style.Theme_Misbar)

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.mainFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.shows_carousel)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.carousel_item), isDisplayed())).perform(
            swipeLeft(), swipeLeft(), swipeLeft(), click()
        )

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailItemFragment)
//       Navigating to DetailItemFragment
        val detailFragmentScenario =
            launchFragmentInContainer<DetailItemFragment>(
                fragmentArgs = bundleOf(
                    "type" to DetailItemType.Shows.type,
                    "itemId" to 4
                ), themeResId = R.style.Theme_Misbar
            )
//
        detailFragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.detailItemFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(LocalItems.localShows[3].title)))
    }

    @Test
    fun testListScrollToPosition_click() {
//        Scroll to the 5th position of the list, then click it
//        It will navigate to DetailItemFragment with corresponding data
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<ShowsFragment>(themeResId = R.style.Theme_Misbar)

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.mainFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.rv_popular_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailItemFragment)
//       Navigating to DetailItemFragment
        val detailFragmentScenario =
            launchFragmentInContainer<DetailItemFragment>(
                fragmentArgs = bundleOf(
                    "type" to DetailItemType.Shows.type,
                    "itemId" to 5
                ), themeResId = R.style.Theme_Misbar
            )
//
        detailFragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.detailItemFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(LocalItems.localShows[4].title)))
    }
}