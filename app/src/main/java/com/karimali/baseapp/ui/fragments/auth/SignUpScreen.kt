package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.getValue
import com.karimali.baseapp.common.extensions.isValidatePhone
import com.karimali.baseapp.databinding.FragmentSignUpScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpScreen : BaseFragment<FragmentSignUpScreenBinding>
    (FragmentSignUpScreenBinding::inflate,R.layout.fragment_sign_up_screen) {
    private val args:SignUpScreenArgs by navArgs()
    private val authViewModel: AuthViewModel by viewModels()

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
                    sendCode()
            }
        }
    }

    private fun sendCode() {

        authViewModel.sendCode(binding!!.phoneInput.getEditText().getValue())
            .observe(viewLifecycleOwner){
                stateHandler(
                    result = it,
                    loadingButton = binding!!.signUpBtu,
                    showToasts = true,
                    onSuccess = {
                        navigateToVerifyPhone()
                    }
                )
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