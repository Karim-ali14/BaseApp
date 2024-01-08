package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentSignUpScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class SignUpScreen : BaseFragment<FragmentSignUpScreenBinding>
    (FragmentSignUpScreenBinding::inflate,R.layout.fragment_sign_up_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventClicks()
    }

    private fun eventClicks() {
        binding!!.apply {
            signUpBtu.setOnClickListener {
                navigateToVerifyPhone()
            }
        }
    }

    private fun navigateToVerifyPhone() {
        navController!!.navigate(
            R.id.action_signUpScreen_to_verificationScreen
        )
    }
}