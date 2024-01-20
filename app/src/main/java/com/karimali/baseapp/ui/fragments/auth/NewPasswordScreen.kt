package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.confirmPasswordValidation
import com.karimali.baseapp.common.extensions.getValue
import com.karimali.baseapp.common.extensions.isValidPassword
import com.karimali.baseapp.databinding.FragmentNewPasswordScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPasswordScreen : BaseFragment<FragmentNewPasswordScreenBinding>
    (FragmentNewPasswordScreenBinding::inflate,R.layout.fragment_new_password_screen) {
    private val authViewModel:AuthViewModel by viewModels()
    val args:NewPasswordScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
    }

    private fun isDateValidate(): Boolean {
        var validate = false

        binding!!.apply {
            if (passwordInput.isValidPassword() &&
                confirmPasswordInput.isValidPassword() &&
                passwordInput.confirmPasswordValidation(confirmPasswordInput)){
                validate = true
            }
        }
        return validate
    }

    private fun eventClicks() {
        binding!!.apply {
            doneBtu.setOnClickListener {
                if (isDateValidate())
                    changePassword()
            }
        }
    }

    private fun changePassword() {
        authViewModel.resetPassword(
            phone = args.phone,
            code = args.code,
            password = binding!!.passwordInput.getEditText().getValue(),
            confirmPassword = binding!!.confirmPasswordInput.getEditText().getValue(),
        ).observe(viewLifecycleOwner){
            stateHandler(
                result = it,
                loadingButton = binding!!.doneBtu,
                showToasts = true,
                onSuccess = {
                    navigateToLogin()
                }
            )
        }
    }

    private fun navigateToLogin() {
        navController!!.navigate(
            NewPasswordScreenDirections.actionNewPasswordScreenToLoginScreen()
        )
    }
}