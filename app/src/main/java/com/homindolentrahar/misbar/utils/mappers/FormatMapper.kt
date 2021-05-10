package com.homindolentrahar.misbar.utils.mappers

import android.icu.text.CompactDecimalFormat
import android.icu.text.SimpleDateFormat
import java.util.*

object FormatMapper {
    fun formatReleaseDate(dateStr: String): String {
        return if (dateStr.isNotEmpty()) {
            val parsedDate = SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(dateStr)
            SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(parsedDate)
        } else {
            "No Release Date"
        }
    }

    fun formatRevenueString(revenue: Int?): String =
        CompactDecimalFormat
            .getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT)
            .format(revenue) ?: "No Revenue"
}