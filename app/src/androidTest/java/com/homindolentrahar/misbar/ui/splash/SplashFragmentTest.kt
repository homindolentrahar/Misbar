package com.homindolentrahar.misbar.ui.splash

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashFragmentTest {

    @Test
    fun testNavigationSplashToMain() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val fragmentScenario =
            launchFragmentInContainer<SplashFragment>(themeResId = R.style.Theme_Misbar)

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.app_navigation)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Thread.sleep(1750)
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }
}