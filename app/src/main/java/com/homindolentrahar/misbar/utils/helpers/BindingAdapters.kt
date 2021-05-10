package com.homindolentrahar.misbar.utils.helpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.utils.mappers.FormatMapper

@BindingAdapter("directorVisibility")
fun bindDirectorVisibility(container: ConstraintLayout, director: String) {
    container.visibility =
        if (director.isNotEmpty()) View.VISIBLE
        else View.GONE
}

@BindingAdapter("revenueVisibility")
fun bindRevenueVisibility(container: ConstraintLayout, revenue: Int) {
    container.visibility =
        if (revenue == 0) View.INVISIBLE
        else View.VISIBLE
}

@BindingAdapter("genresChip", "ratingChip")
fun bindChips(chipGroup: ChipGroup, genres: List<String>, rating: Double) {
    chipGroup.removeAllViews()

    genres.forEach { genre ->
        val chip = Chip(chipGroup.context).apply {
            id = R.id.genre_item_chip
            setChipCornerRadiusResource(R.dimen.radius)
            setChipBackgroundColorResource(R.color.dark)
            text = genre.trim()
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setTextAppearanceResource(R.style.ChipTitle)
            setChipStartPaddingResource(R.dimen.small_spacing)
            setChipEndPaddingResource(R.dimen.small_spacing)
            isClickable = false
            isEnabled = false
        }
        chipGroup.addView(chip)
    }
    chipGroup.addView(Chip(chipGroup.context).apply {
        id = R.id.rating_chip
        setChipCornerRadiusResource(R.dimen.radius)
        chipIcon = ContextCompat.getDrawable(chipGroup.context, R.drawable.ic_rating)
        setChipIconSizeResource(R.dimen.chip_icon_size)
        setIconStartPaddingResource(R.dimen.small_spacing)
        setIconEndPaddingResource(R.dimen.half_small_spacing)
        setChipBackgroundColorResource(R.color.black)
        setChipStrokeColorResource(R.color.colorPrimary)
        setChipStrokeWidthResource(R.dimen.chip_stroke_width)
        text = rating.toString()
        setTextAppearanceResource(R.style.ChipTitle_Primary)
        setChipStartPaddingResource(R.dimen.small_spacing)
        setChipEndPaddingResource(R.dimen.small_spacing)
        isClickable = false
        isEnabled = false
    })
}

@BindingAdapter("imagePoster")
fun bindImagePoster(imageView: ImageView, imgSrc: Int) {
    Glide.with(imageView)
        .load(imgSrc)
        .apply(
            RequestOptions
                .placeholderOf(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
        )
        .into(imageView)
}

@BindingAdapter("itemGenres")
fun bindItemGenres(textView: TextView, genres: List<String>) {
    textView.text = genres.joinToString(", ")
}

@BindingAdapter("revenueInt")
fun bindRevenue(textView: TextView, revenue: Int) {
    val formattedRevenue = FormatMapper.formatRevenueString(revenue)
    textView.text = formattedRevenue
}

@BindingAdapter("releaseDate")
fun bindReleaseDate(textView: TextView, releaseStr: String) {
    val formattedReleaseDate = FormatMapper.formatReleaseDate(releaseStr)
    textView.text = formattedReleaseDate
}