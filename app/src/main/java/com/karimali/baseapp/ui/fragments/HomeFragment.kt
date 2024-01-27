package com.karimali.baseapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.onBackButtonPressed
import com.karimali.baseapp.common.extensions.setup
import com.karimali.baseapp.common.extensions.toArrayList
import com.karimali.baseapp.databinding.FragmentHomeBinding
import com.karimali.baseapp.date.models.home.BannerModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import com.karimali.baseapp.date.models.home.ProductCategoryModel
import com.karimali.baseapp.date.models.home.ServiceModel
import com.karimali.baseapp.ui.adapters.AdapterBindings
import com.karimali.baseapp.ui.adapters.GenericRecyclerAdapter
import com.karimali.baseapp.ui.adapters.HomeAdapter
import com.karimali.baseapp.ui.adapters.setup
import com.karimali.baseapp.ui.base.BaseFragment
import com.karimali.baseapp.ui.viewModles.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate,R.layout.fragment_home) {

    private val homeViewModel:HomeViewModel by viewModels()

    private lateinit var homeAdapter:HomeAdapter
    private lateinit var bannerAdapter:GenericRecyclerAdapter<BannerModel>
    private lateinit var sectionAdapter:GenericRecyclerAdapter<CategoryModel>
    private lateinit var productCategoryAdapter:GenericRecyclerAdapter<ProductCategoryModel>
    private lateinit var serviceAdapter:GenericRecyclerAdapter<ServiceModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapters()
    }

    private fun initAdapters() {
        bannerAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.image_banner_item_layout,
            AdapterBindings.bannerItemBinding()
        )
        sectionAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.section_item_layout,
            AdapterBindings.sectionItemBinding()
        )
        productCategoryAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.see_all_title_item_layout,
            AdapterBindings.productCategoryItemBinding()
        )
        serviceAdapter = GenericRecyclerAdapter(
            arrayListOf(),
            R.layout.service_horizontal_item_layout,
            AdapterBindings.serviceItemBinding()
        )
        homeAdapter = HomeAdapter(
            bannerAdapter = bannerAdapter,
            sectionAdapter = sectionAdapter,
            productCategoriesAdapter = productCategoryAdapter,
            servicesAdapter = serviceAdapter
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPressed {
            requireActivity().finishAffinity()
        }
        fetchHomeDate()
        bindDate()
    }

    private fun bindDate() {
        binding!!.apply {
            homeDateRecycler.adapter = homeAdapter
            homeDateRecycler.layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun fetchHomeDate() {
        homeViewModel.fetchHomeDate()
            .observe(viewLifecycleOwner){ resultState ->
                stateHandler(
                    result = resultState,
                    isWithDialog = true,
                    onSuccess = {
                        updateAdapters(it)
                    }
                )
            }
    }

    private fun updateAdapters(homeDateModel: HomeDateModel?) {
        bannerAdapter.updateData(arrayListOf(BannerModel(R.drawable.banner_ic)))
        sectionAdapter.updateData(homeDateModel?.categories)
        productCategoryAdapter.updateData(homeDateModel?.products)
        serviceAdapter.updateData(homeDateModel?.services)
    }
}