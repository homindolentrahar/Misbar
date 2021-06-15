package com.homindolentrahar.misbar.ui.splash

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class SplashFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

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
    fun testNavigationSplashToMain() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<SplashFragment>(themeResId = R.style.Theme_Misbar)

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Thread.sleep(750)
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }
}