package com.karimali.baseapp.ui.fragments

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.onBackButtonPressed
import com.karimali.baseapp.databinding.FragmentHomeBinding
import com.karimali.baseapp.ui.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate,R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPressed {
            requireActivity().finishAffinity()
        }
    }
}