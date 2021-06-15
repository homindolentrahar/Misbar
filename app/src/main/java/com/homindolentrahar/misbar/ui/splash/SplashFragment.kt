package com.homindolentrahar.misbar.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.databinding.FragmentSplashBinding
import com.homindolentrahar.misbar.utils.EspressoIdlingResource

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Start animation
        val fadeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.logo_animation)
        binding.imgLogo.startAnimation(fadeAnimation)
//        Navigate to main screen
        fadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                EspressoIdlingResource.increment()
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())

                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }, 750)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}