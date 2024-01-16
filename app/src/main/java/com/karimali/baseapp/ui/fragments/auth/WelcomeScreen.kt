package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.onBackButtonPressed
import com.karimali.baseapp.databinding.FragmentWelcomeScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class WelcomeScreen : BaseFragment<FragmentWelcomeScreenBinding>
    ( FragmentWelcomeScreenBinding::inflate,R.layout.fragment_welcome_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsClick()
        onBackButtonPressed {
            requireActivity().finishAffinity()
        }
    }

    private fun eventsClick() {
        binding!!.apply {
            signInBtu.setOnClickListener {
                navController!!.navigate(
                    R.id.action_welcomeScreen_to_loginScreen
                )
            }
            signUpBtu.setOnClickListener {
                navController!!.navigate(
                    R.id.action_welcomeScreen_to_signUpScreen
                )
            }
        }
    }
}