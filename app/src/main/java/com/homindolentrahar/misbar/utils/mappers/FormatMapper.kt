package com.homindolentrahar.misbar.utils.mappers

import android.icu.text.CompactDecimalFormat
import android.icu.text.SimpleDateFormat
import com.homindolentrahar.misbar.others.constants.Constants
import java.util.*

object FormatMapper {
    fun formatReleaseDate(dateStr: String): String {
        return if (!listOf(Constants.NO_AIRING, Constants.NO_RELEASE, "").contains(dateStr)) {
            val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateStr)
            SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(parsedDate)
        } else {
            "No Release Date"
        }
    }

    fun formatRuntime(runtime: Int): String {
        val hourStr = if (runtime.div(60) > 0) {
            "${runtime.div(60)}h"
        } else {
            ""
        }
        val minuteStr = "${runtime.rem(60)}m"

        return "$hourStr $minuteStr".trim()
    }

    fun formatRevenueString(revenue: Int): String =
        if (revenue > 0) CompactDecimalFormat
            .getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
            .format(revenue)
        else "No Revenue"
}