package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.FragmentShowAllDataBinding
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
abstract class ShowAllDataFragment : BaseFragment<FragmentShowAllDataBinding>
    (FragmentShowAllDataBinding::inflate,R.layout.fragment_show_all_data) {
   val homeViewModel: HomeViewModel by viewModels()

    abstract val type:Enums.HomeItemsType

    abstract fun bindData()
    abstract fun initAdapter()
}