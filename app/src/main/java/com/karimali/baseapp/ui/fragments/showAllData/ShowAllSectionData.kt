package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.custom.RecyclerLayoutTypes

class ShowAllSectionData :ShowAllDataFragment(){
    override val type: Enums.HomeItemsType = Enums.HomeItemsType.Section
    private lateinit var sectionAdapter:GenericRecyclerAdapter<CategoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun bindData() {
        binding!!.apply {
            type = this@ShowAllSectionData.type
            recycler.addAdapter(sectionAdapter, recyclerType = RecyclerLayoutTypes.Flexed, shimmerLayoutRes = R.layout.shimmerd_section_item_layout)
        }
    }

    override fun initAdapter() {
        sectionAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.section_item_layout,
            AdapterBindings.sectionItemBinding()
        )
    }

}