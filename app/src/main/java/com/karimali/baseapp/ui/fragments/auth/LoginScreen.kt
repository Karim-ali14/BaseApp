package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.isEmptyFieldValidation
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.FragmentLoginScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class LoginScreen : BaseFragment<FragmentLoginScreenBinding>
    (FragmentLoginScreenBinding::inflate,R.layout.fragment_login_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsClick()
    }

    private fun validateDate(): Boolean
    = binding!!.phoneInput.isEmptyFieldValidation() && binding!!.passwordInput.isEmptyFieldValidation()

    private fun eventsClick() {
        binding!!.apply {
            signUpBtu.setOnClickListener {
                navigateToSignUp()
            }

            forgetPasswordBtu.setOnClickListener {
                navigateToForgetPassword()
            }

            signInBtu.setOnClickListener {
                if (validateDate()){

                }
            }
        }
    }

    private fun navigateToSignUp() {
        navController!!.navigate(
            LoginScreenDirections.actionLoginScreenToSignUpScreen(
                Enums.NavigationTypes.SignUp
            )
        )
    }

    private fun navigateToForgetPassword() {
        navController!!.navigate(
            LoginScreenDirections.actionLoginScreenToSignUpScreen(
                Enums.NavigationTypes.ResetPassword
            )
        )
    }
}