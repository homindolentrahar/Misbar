package com.homindolentrahar.misbar.others.constants

import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.domain.models.LocalGenresModel

enum class ItemType { Movies, Shows }

object Constants {
    const val NO_OVERVIEW = "No Overview"
    const val NO_TITLE = "No Original Title"
    const val NO_RELEASE = "No Release Date"
    const val NO_AIRING = "No Airing Date"
    const val NO_NAME = "No Name"
    const val NO_COUNTRY = "No Country"
    const val THEME_EXTRAS_BUNDLE_KEY = "\"androidx.fragment.app.testing.FragmentScenario\" +\n" +
            "                \".EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY\""
    const val DEFAULT_PAGE_INDEX = 1

    fun getLocalGenres(type: ItemType): List<LocalGenresModel> = listOf(
        LocalGenresModel(
            if (type == ItemType.Movies)
                28
            else
                10759,
            if (type == ItemType.Movies)
                "Action"
            else
                "Action & Adventure",
            R.drawable.ic_action,
            "The Gun, the Punch, and the Blood. Seems pretty normal"
        ),
        LocalGenresModel(
            35,
            "Comedy",
            R.drawable.ic_comedy,
            "Slow down your life, sit back and laugh"
        ),
        LocalGenresModel(
            16,
            "Animation",
            R.drawable.ic_animation,
            "Sometimes fantasy reflect our world we lived in"
        ),
        LocalGenresModel(18, "Drama", R.drawable.ic_drama, "If you wanna cry, just let it all out"),
        LocalGenresModel(
            if (type == ItemType.Movies)
                27
            else
                9648,
            if (type == ItemType.Movies)
                "Horror"
            else
                "Mystery",
            R.drawable.ic_horror,
            "I thought you\'re alone, who\'s the person behind you?"
        ),
        LocalGenresModel(
            if (type == ItemType.Movies)
                36
            else
                99,
            if (type == ItemType.Movies)
                "History"
            else
                "Documentary",
            R.drawable.ic_history,
            "We learn from the past to fix our future"
        ),
        LocalGenresModel(
            if (type == ItemType.Movies)
                878
            else
                10765,
            if (type == ItemType.Movies)
                "Science Fiction"
            else
                "Science Fiction & Fantasy",
            R.drawable.ic_sci_fi,
            "Spaceship goes brrrrrr"
        ),
        LocalGenresModel(
            if (type == ItemType.Movies)
                10749
            else
                10751,
            if (type == ItemType.Movies)
                "Romance"
            else
                "Family",
            R.drawable.ic_romance,
            if (type == ItemType.Movies)
                "I hope we\'re never apart in other universe"
            else
                "A place we called home"
        ),
    )
}