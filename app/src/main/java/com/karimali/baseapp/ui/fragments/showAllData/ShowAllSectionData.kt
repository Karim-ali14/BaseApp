package com.karimali.baseapp.ui.fragments.showAllData

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.custom.RecyclerLayoutTypes

class ShowAllSectionData :ShowAllDataFragment(){
    override val type: Enums.HomeItemsType = Enums.HomeItemsType.Section
    private lateinit var sectionAdapter:GenericRecyclerAdapter<CategoryModel>
    private val args:ShowAllSectionDataArgs by navArgs()
    val categories = arrayListOf<CategoryModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        fetchAllSections()
    }
    override fun bindData() {
        binding!!.apply {
            type = this@ShowAllSectionData.type
            recycler.addAdapter(sectionAdapter, recyclerType = RecyclerLayoutTypes.Linear , shimmerLayoutRes = R.layout.shimmerd_section_item_layout)
        }
    }
    private fun fetchAllSections() {
        homeViewModel.fetchCategoriesDate()
            .observe(viewLifecycleOwner){
            stateHandler(
                result = it,
                withShimmer = binding!!.recycler,
                onSuccess = {
                    binding!!.numberOfItems.text = String.format(getString(
                        R.string.browse_more_than_,
                        it?.size.toString(),
                        getString(R.string.products))
                    )
                    categories.clear()
                    categories.addAll(it?: arrayListOf())
                    sectionAdapter.notifyDataSetChanged()
                }
            )
        }
    }
    override fun initAdapter() {
        sectionAdapter = GenericRecyclerAdapter(
            categories,
            R.layout.section_item_layout,
            AdapterBindings.sectionItemBinding()
        )
    }
}