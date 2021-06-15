package com.homindolentrahar.misbar.ui.core

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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

fun withDrawable(@DrawableRes resource: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("ImageView with drawable same as drawable with id $resource")
    }

    override fun matchesSafely(item: View): Boolean {
        val context = item.context
        val expectedBitmap = ContextCompat.getDrawable(context, resource)?.toBitmap()

        return item is ImageView && item.drawable.toBitmap().sameAs(expectedBitmap)
    }
}