package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentLoginScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class LoginScreen : BaseFragment<FragmentLoginScreenBinding>
    (FragmentLoginScreenBinding::inflate,R.layout.fragment_login_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}