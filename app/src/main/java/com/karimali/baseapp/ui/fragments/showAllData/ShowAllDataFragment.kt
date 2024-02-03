package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import android.view.View
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.FragmentShowAllDataBinding
import com.karimali.baseapp.ui.base.BaseFragment


abstract class ShowAllDataFragment : BaseFragment<FragmentShowAllDataBinding>
    (FragmentShowAllDataBinding::inflate,R.layout.fragment_show_all_data) {
    abstract val type:Enums.HomeItemsType
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    abstract fun bindData()
    abstract fun initAdapter()
}