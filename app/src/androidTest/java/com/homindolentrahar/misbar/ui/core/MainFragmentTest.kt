package com.homindolentrahar.misbar.ui.core

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.homindolentrahar.misbar.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainFragmentTest {

    @Test
    fun testTabLayoutMenu() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<MainFragment>(themeResId = R.style.Theme_Misbar)

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.mainFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
//        Click
        onView(withText("Shows")).perform(click())
        onView(withId(R.id.shows_fragment)).check(matches(isDisplayed()))

//        Swipe
        onView(withId(R.id.view_pager)).perform(swipeRight())
        onView(withId(R.id.movies_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.shows_fragment)).check(matches(isDisplayed()))

//        Click
        onView(withText("Movies")).perform(click())
        onView(withId(R.id.movies_fragment)).check(matches(isDisplayed()))
    }
}