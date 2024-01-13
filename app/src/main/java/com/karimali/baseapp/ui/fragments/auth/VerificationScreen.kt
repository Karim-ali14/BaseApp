package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.common.utils.Enums.NavigationTypes.SignUp
import com.karimali.baseapp.databinding.FragmentVerificationScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class VerificationScreen : BaseFragment<FragmentVerificationScreenBinding>
    (FragmentVerificationScreenBinding::inflate,R.layout.fragment_verification_screen) {
    private val args:VerificationScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
    }

    private fun eventClicks() {
        binding!!.apply {
            sendSummary.setOnClickListener {
                if (args.type == Enums.NavigationTypes.SignUp){
                    navigateToCompleteProfile()
                }else if (args.type == Enums.NavigationTypes.ResetPassword){
                    navigateToResetPassword()
                }
            }
        }
    }

    private fun navigateToResetPassword() {
        navController!!.navigate(
            R.id.action_verificationScreen_to_completeProfileScreen
        )
    }

    private fun navigateToCompleteProfile() {
        navController!!.navigate(
            R.id.action_verificationScreen_to_completeProfileScreen
        )
    }
}