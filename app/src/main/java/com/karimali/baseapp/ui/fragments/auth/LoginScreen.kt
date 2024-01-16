package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.getValue
import com.karimali.baseapp.common.extensions.isEmptyFieldValidation
import com.karimali.baseapp.common.utils.Constants
import com.karimali.baseapp.common.utils.Constants.Keys.USER_KEY
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.FragmentLoginScreenBinding
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.di.AppSharedPrefs
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginScreen : BaseFragment<FragmentLoginScreenBinding>
    (FragmentLoginScreenBinding::inflate,R.layout.fragment_login_screen) {

    private val authViewModel:AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: AppSharedPrefs


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
                    login()
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

    private fun login(){
        authViewModel.login(
            binding!!.phoneInput.getEditText().getValue(),
            binding!!.passwordInput.getEditText().getValue()
        ).observe(viewLifecycleOwner){ resultState ->
            stateHandler(
                result = resultState,
                loadingButton = binding!!.signInBtu,
                showToasts = true,
                onSuccess = {
                    saveClientDate(it)
                }
            )
        }
    }

    private fun saveClientDate(clientModel: ClientModel?) {
        sharedPrefs.storeData(USER_KEY,clientModel!!)
    }
}