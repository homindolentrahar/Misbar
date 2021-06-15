package com.homindolentrahar.misbar.utils.animation

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.R

object AnimationUtils {
    fun expand(parentView: ViewGroup) {
        val animatedView = parentView.findViewById<ConstraintLayout>(R.id.content_container)

        animatedView.measure(
            ViewGroup.LayoutParams.MATCH_PARENT,
            if (animatedView is RecyclerView)
                216
            else
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val targetHeight: Int = animatedView.measuredHeight

        animatedView.layoutParams.height = 1
        animatedView.visibility = View.VISIBLE

        val expandAnimation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                animatedView.layoutParams.height =
                    if (interpolatedTime == 1f)
                        if (animatedView is RecyclerView)
                            216
                        else
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    else
                        (targetHeight * interpolatedTime).toInt()
                animatedView.requestLayout()
            }

            override fun willChangeBounds(): Boolean = true
        }
        expandAnimation.duration =
            (targetHeight / getViewDensity(animatedView)).toLong()
        animatedView.startAnimation(expandAnimation)
//        Change Button
        parentView.findViewById<ImageButton>(R.id.btn_dropdown)
            .setImageResource(R.drawable.ic_up)
    }

    fun collapse(parentView: ViewGroup) {
        val animatedView = parentView.findViewById<ConstraintLayout>(R.id.content_container)
        val initialHeight: Int = animatedView.measuredHeight

        val collapsedAnimation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    animatedView.visibility = View.GONE
                } else {
                    animatedView.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                }
                animatedView.requestLayout()
            }

            override fun willChangeBounds(): Boolean = true
        }

        collapsedAnimation.duration =
            (initialHeight / getViewDensity(animatedView)).toLong()
        animatedView.startAnimation(collapsedAnimation)
//        Change Button
        parentView.findViewById<ImageButton>(R.id.btn_dropdown)
            .setImageResource(R.drawable.ic_down)
    }

    private fun getViewDensity(view: View) = view.context.resources.displayMetrics.density
}