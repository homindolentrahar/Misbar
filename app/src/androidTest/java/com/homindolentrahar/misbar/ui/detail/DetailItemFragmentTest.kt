package com.homindolentrahar.misbar.ui.detail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.ui.core.nthChildOf
import com.homindolentrahar.misbar.utils.mappers.FormatMapper
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailItemFragmentTest {

    @Test
    fun testBackButton() {
//        Test the back button, pop the back stack
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<DetailItemFragment>(
                bundleOf(
                    "type" to DetailItemType.Movies.type,
                    "itemId" to 10
                ), themeResId = R.style.Theme_Misbar
            )

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)
            navController.setCurrentDestination(R.id.detailItemFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.back_button)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.back_button)).perform(click())
    }

    @Test
    fun testLoadCorrespondingData() {
//        Test the back button, pop the back stack
        launchFragmentInContainer<DetailItemFragment>(
            bundleOf(
                "type" to DetailItemType.Movies.type,
                "itemId" to 10
            ), themeResId = R.style.Theme_Misbar
        )

        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.genres_chip)).check(matches(hasChildCount(4)))
        onView(allOf(withId(R.id.genre_item_chip), isDisplayed()))
        onView(
            nthChildOf(
                withId(R.id.genres_chip),
                0
            )
        ).check(matches(withText(LocalItems.localMovies[9].genres[0])))
        onView(
            nthChildOf(
                withId(R.id.genres_chip),
                1
            )
        ).check(matches(withText(LocalItems.localMovies[9].genres[1])))
        onView(
            nthChildOf(
                withId(R.id.genres_chip),
                2
            )
        ).check(matches(withText(LocalItems.localMovies[9].genres[2])))
        onView(withId(R.id.rating_chip)).check(matches(withText(LocalItems.localMovies[9].rating.toString())))
        onView(withId(R.id.tv_title)).check(matches(withText(LocalItems.localMovies[9].title)))
        onView(withId(R.id.tv_duration)).check(matches(withText(LocalItems.localMovies[9].duration)))
        onView(withId(R.id.tv_release)).check(
            matches(
                withText(
                    FormatMapper.formatReleaseDate(
                        LocalItems.localMovies[9].releaseDate
                    )
                )
            )
        )
        onView(withId(R.id.tv_director)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_director)).check(matches(withText(LocalItems.localMovies[9].director)))
        onView(withId(R.id.tv_synopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_lang)).check(matches(withText(LocalItems.localMovies[9].language)))
        onView(withId(R.id.tv_revenue)).check(
            matches(
                withText(
                    FormatMapper.formatRevenueString(
                        LocalItems.localMovies[9].revenue
                    )
                )
            )
        )
        onView(withId(R.id.tv_age_rating_logo)).check(matches(withText(LocalItems.localMovies[9].ageRating)))
        onView(withId(R.id.tv_age_rating)).check(matches(withText(LocalItems.localMovies[9].ageRating)))
    }
}