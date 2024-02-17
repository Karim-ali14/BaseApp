package com.karimali.baseapp.ui.fragments.productDetails

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.FragmentProductDetailsBinding
import com.karimali.baseapp.ui.base.BaseFragment

class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>
    (FragmentProductDetailsBinding::inflate,R.layout.fragment_product_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}