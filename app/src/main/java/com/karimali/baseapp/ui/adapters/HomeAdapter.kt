package com.karimali.baseapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.BannerItemLayoutBinding
import com.karimali.baseapp.databinding.RecyclerItemLayoutBinding
import com.karimali.baseapp.databinding.SeeAllTitleItemLayoutBinding
import com.karimali.baseapp.date.models.home.BannerModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.ProductCategoryModel
import com.karimali.baseapp.date.models.home.ServiceModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class HomeAdapter(
    val bannerAdapter: GenericRecyclerAdapter<BannerModel>?,
    val sectionAdapter: GenericRecyclerAdapter<CategoryModel>?,
    val productCategoriesAdapter: GenericRecyclerAdapter<ProductCategoryModel>?,
    val servicesAdapter: GenericRecyclerAdapter<ServiceModel>?,
    val showAllSection:() -> Unit,
    val showAllServices:() -> Unit,
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class BannerViewHolder(val binding : BannerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindDate(){
            binding.apply {
                pager.adapter = bannerAdapter
                pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        binding.dotsIndicator.onPageScrolled(position, 0f, 0)
                    }

                })
                dotsIndicator.apply {
                    setSliderColor(
                        Color.parseColor("#CFD2DC"),
                        Color.parseColor("#12B3A8")
                    )
                    setSliderWidth(resources.getDimension(com.intuit.sdp.R.dimen._15sdp))
                    setSliderHeight(resources.getDimension(com.intuit.sdp.R.dimen._2sdp))
                    setSlideMode(IndicatorSlideMode.WORM)
                    setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                    setPageSize(pager.adapter!!.itemCount)
                    notifyDataChanged()
                }
            }
        }
    }

    inner class SectionViewHolder(val binding :SeeAllTitleItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindDate(){
            binding.apply {
                binding.title = binding.root.context.getString(R.string.sections)
                binding.seeAllBtu.setOnClickListener {
                    showAllSection()
                }
                binding.titleTxt.handleVisibility(sectionAdapter?.itemCount != 0)
                binding.seeAllBtu.handleVisibility(sectionAdapter?.itemCount != 0)
                sectionAdapter?.apply {
                    binding.recycler.setup(this, isHorizontal = true)
                }
            }
        }
    }

    inner class ProductCategoriesViewHolder(val binding : RecyclerItemLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        fun bindDate(){
            binding.apply {
                productCategoriesAdapter?.apply {
                    binding.recycler.setup(this)
                }
            }
        }
    }

    inner class ServiceViewHolder(val binding :SeeAllTitleItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindDate(){
            binding.apply {
                binding.title = binding.root.context.getString(R.string.services)
                binding.seeAllBtu.setOnClickListener {
                    showAllServices()
                }

                binding.titleTxt.handleVisibility(servicesAdapter?.itemCount != 0)
                binding.seeAllBtu.handleVisibility(servicesAdapter?.itemCount != 0)
                servicesAdapter?.apply {
                    binding.recycler.setup(this, isHorizontal = true)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Enums.HomeItemsType.Banners.ordinal -> {
                BannerViewHolder(
                    BannerItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            Enums.HomeItemsType.Section.ordinal -> {
                SectionViewHolder(
                    SeeAllTitleItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            Enums.HomeItemsType.Products.ordinal -> {
                ProductCategoriesViewHolder(
                    RecyclerItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            Enums.HomeItemsType.Services.ordinal -> {
                ServiceViewHolder(
                    SeeAllTitleItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                BannerViewHolder(
                    BannerItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position){
            Enums.HomeItemsType.Banners.ordinal -> {
                (holder as BannerViewHolder).bindDate()
            }
            Enums.HomeItemsType.Section.ordinal -> {
                (holder as SectionViewHolder).bindDate()
            }
            Enums.HomeItemsType.Products.ordinal -> {
                (holder as ProductCategoriesViewHolder).bindDate()
            }
            Enums.HomeItemsType.Services.ordinal -> {
                (holder as ServiceViewHolder).bindDate()
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position

}