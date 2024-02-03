package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.ProductModel
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.custom.RecyclerLayoutTypes

class ShowAllProductData :ShowAllDataFragment(){
    override val type: Enums.HomeItemsType = Enums.HomeItemsType.Products

    private lateinit var sectionAdapter:GenericRecyclerAdapter<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun bindData() {
        binding!!.apply {
            type = this@ShowAllProductData.type
            recycler.addAdapter(sectionAdapter, recyclerType = RecyclerLayoutTypes.Grid,cols = 2, shimmerLayoutRes = R.layout.shimmerd_product_item_layout)
        }
    }

    override fun initAdapter() {
        sectionAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.product_item_layout,
            AdapterBindings.productItemBinding()
        )
    }

}