package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.date.models.home.ProductModel
import com.karimali.baseapp.ui.activities.MainActivity
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.custom.RecyclerLayoutTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowAllProductData :ShowAllDataFragment(){

    override val type: Enums.HomeItemsType = Enums.HomeItemsType.Products

    private lateinit var productAdapter:GenericRecyclerAdapter<ProductModel>

    private val args:ShowAllProductDataArgs by navArgs()

    private val products:ArrayList<ProductModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTagTitle()
        bindData()
        fetchAllProductsByTagId()
    }

    private fun setTagTitle() {
        (activity as MainActivity).setToolBarTitle(args.productCategoryItem.name)
    }

    private fun fetchAllProductsByTagId() {
        homeViewModel.fetchAllProductData(
            args.productCategoryItem.id.toString()
        ).observe(viewLifecycleOwner){
            stateHandler(
                result = it,
                withShimmer = binding!!.recycler,
                onSuccess = {
                    productAdapter.updateData(it)
                }
            )
        }
    }

    override fun bindData() {
        binding!!.apply {
            type = this@ShowAllProductData.type
            recycler.addAdapter(productAdapter, recyclerType = RecyclerLayoutTypes.Grid,cols = 2, shimmerLayoutRes = R.layout.shimmerd_product_item_layout)
        }
    }

    override fun initAdapter() {
        productAdapter = GenericRecyclerAdapter(
            products,
            R.layout.product_item_layout,
            AdapterBindings.productItemBinding()
        )
    }

}