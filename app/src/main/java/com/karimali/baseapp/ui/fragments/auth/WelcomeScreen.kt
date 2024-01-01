package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentWelcomeScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class WelcomeScreen : BaseFragment<FragmentWelcomeScreenBinding>
    ( FragmentWelcomeScreenBinding::inflate,R.layout.fragment_welcome_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}