package com.karimali.baseapp.ui.fragments


import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentSplashScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment
import kotlinx.coroutines.delay


class SplashScreen : BaseFragment<FragmentSplashScreenBinding>
    (FragmentSplashScreenBinding::inflate,R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.image.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.alpha_scal_anim))

        lifecycleScope.launchWhenResumed {
            delay(3000)

            navController!!.navigate(
                SplashScreenDirections.actionSplashScreenToAuthNavigation()
            )
        }

    }
}