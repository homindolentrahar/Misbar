package com.homindolentrahar.misbar.ui.core

import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int) =
    object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("position $childPosition of parent")
            parentMatcher.describeTo(description)
        }

        override fun matchesSafely(item: View?): Boolean {
            if (item?.parent !is ViewGroup) return false
            val parent = item.parent as ViewGroup

            return parentMatcher.matches(parent)
                    && parent.childCount > childPosition
                    && parent.getChildAt(childPosition).equals(item)
        }
    }