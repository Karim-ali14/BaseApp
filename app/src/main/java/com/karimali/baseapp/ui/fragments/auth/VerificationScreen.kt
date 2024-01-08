package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentVerificationScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class VerificationScreen : BaseFragment<FragmentVerificationScreenBinding>
    (FragmentVerificationScreenBinding::inflate,R.layout.fragment_verification_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClicks()
    }

    private fun eventClicks() {
        binding!!.apply {
            sendSummary.setOnClickListener {
                navigateToCompleteProfile()
            }
        }
    }

    private fun navigateToCompleteProfile() {
        navController!!.navigate(
            R.id.action_verificationScreen_to_completeProfileScreen
        )
    }
}