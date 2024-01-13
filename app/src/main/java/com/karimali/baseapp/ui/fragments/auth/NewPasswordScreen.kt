package com.karimali.baseapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentNewPasswordScreenBinding
import com.karimali.baseapp.ui.base.BaseFragment


class NewPasswordScreen : BaseFragment<FragmentNewPasswordScreenBinding>
    (FragmentNewPasswordScreenBinding::inflate,R.layout.fragment_new_password_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}