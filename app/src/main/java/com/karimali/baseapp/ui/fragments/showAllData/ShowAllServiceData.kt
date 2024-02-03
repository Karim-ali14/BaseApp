package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.ProductModel
import com.karimali.baseapp.date.models.home.ServiceModel
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.custom.RecyclerLayoutTypes

class ShowAllServiceData :ShowAllDataFragment(){
    override val type: Enums.HomeItemsType = Enums.HomeItemsType.Services

    private lateinit var sectionAdapter:GenericRecyclerAdapter<ServiceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun bindData() {
        binding!!.apply {
            type = this@ShowAllServiceData.type
            recycler.addAdapter(sectionAdapter, recyclerType = RecyclerLayoutTypes.Grid,cols = 2, shimmerLayoutRes = R.layout.shimmer_service_item_layout)
        }
    }

    override fun initAdapter() {
        sectionAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.service_item_layout,
            AdapterBindings.serviceItemBinding()
        )
    }

}