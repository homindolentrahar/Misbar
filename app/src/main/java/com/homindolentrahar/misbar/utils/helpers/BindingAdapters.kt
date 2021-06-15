package com.homindolentrahar.misbar.utils.helpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.homindolentrahar.misbar.BuildConfig
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.domain.models.ShowsCreatedByModel
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.utils.mappers.FormatMapper

@BindingAdapter("genresChip", "ratingChip", requireAll = false)
fun bindChips(chipGroup: ChipGroup, genres: List<GenresModel>, rating: Double) {
    chipGroup.removeAllViews()

    chipGroup.addView(Chip(chipGroup.context).apply {
        id = R.id.rating_chip
        setChipCornerRadiusResource(R.dimen.radius)
        setChipMinHeightResource(R.dimen.chip_size)
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

    genres.forEach { genre ->
        chipGroup.addView(
            Chip(chipGroup.context).apply {
                id = R.id.genre_item_chip
                setChipCornerRadiusResource(R.dimen.radius)
                setChipBackgroundColorResource(R.color.dark)
                text = genre.name
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                setTextAppearanceResource(R.style.ChipTitle)
                setChipStartPaddingResource(R.dimen.small_spacing)
                setChipEndPaddingResource(R.dimen.small_spacing)
                isClickable = false
                isEnabled = false
            }
        )
    }

}

@BindingAdapter("imagePoster")
fun bindImagePoster(imageView: ImageView, imgSrc: String) {
    Glide.with(imageView)
        .load(BuildConfig.IMAGE_URL + imgSrc)
        .apply(
            RequestOptions
                .placeholderOf(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
        )
        .into(imageView)
}

@BindingAdapter("imageResource")
fun bindImageRes(imageView: ImageView, @DrawableRes imgRes: Int) {
    imageView.setImageResource(imgRes)
}

@BindingAdapter("itemGenres")
fun bindItemGenres(textView: TextView, genres: List<GenresModel>) {
    textView.text = genres.joinToString(", ") { it.name }
}

@BindingAdapter("revenueInt")
fun bindRevenue(textView: TextView, revenue: Int) {
    textView.text = textView.resources.getString(
        R.string.revenue_format,
        FormatMapper.formatRevenueString(revenue)
    )
}

@BindingAdapter("releaseDate")
fun bindReleaseDate(textView: TextView, releaseStr: String) {
    textView.text = FormatMapper.formatReleaseDate(releaseStr)
}

@BindingAdapter("runtime")
fun bindRuntime(textView: TextView, runtime: Int) {
    textView.text = FormatMapper.formatRuntime(runtime)
}

@BindingAdapter("productionLocation")
fun bindProductionLocation(textView: TextView, location: List<String>) {
    textView.text = location.joinToString(", ")
}

@BindingAdapter("creator")
fun bindCreator(textView: TextView, creators: List<ShowsCreatedByModel>) {
    textView.text = creators.map { it.name }.joinToString(", ")
}