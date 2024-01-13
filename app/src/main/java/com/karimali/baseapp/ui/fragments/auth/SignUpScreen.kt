package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.getValue
import com.karimali.baseapp.common.extensions.isEmptyFieldValidation
import com.karimali.baseapp.common.extensions.isValidatePhone
import com.karimali.baseapp.databinding.FragmentSignUpScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class SignUpScreen : BaseFragment<FragmentSignUpScreenBinding>
    (FragmentSignUpScreenBinding::inflate,R.layout.fragment_sign_up_screen) {
    private val args:SignUpScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        eventClicks()
    }

    private fun bindData() {
        binding!!.apply {
            this.type = args.type
        }
    }

    private fun eventClicks() {
        binding!!.apply {
            signUpBtu.setOnClickListener {
                if (phoneInput.isValidatePhone())
                    navigateToVerifyPhone()
            }
        }
    }

    private fun navigateToVerifyPhone() {
        navController!!.navigate(
            SignUpScreenDirections.actionSignUpScreenToVerificationScreen(
                args.type ,
                binding!!.phoneInput.getEditText().getValue()
            )
        )
    }
}