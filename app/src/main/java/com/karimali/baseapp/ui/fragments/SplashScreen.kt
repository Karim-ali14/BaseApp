package com.karimali.baseapp.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.databinding.FragmentSplashScreenBinding
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.di.AppSharedPrefs
import com.karimali.baseapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen : BaseFragment<FragmentSplashScreenBinding>
    (FragmentSplashScreenBinding::inflate,R.layout.fragment_splash_screen) {

    @Inject
    lateinit var sharedPrefs : AppSharedPrefs
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.image.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.alpha_scal_anim))
        val user = sharedPrefs.getSavedData<ClientModel>(Constants.Keys.USER_KEY)

        lifecycleScope.launchWhenResumed {
            delay(3000)
//            if(user != null){
//                Log.d("USER","User $user")
//                navController!!.navigate(SplashScreenDirections.actionSplashScreenToHomeNavigation())
//            }else{
                navController!!.navigate(SplashScreenDirections.actionSplashScreenToAuthNavigation())
//            }
        }

    }
}