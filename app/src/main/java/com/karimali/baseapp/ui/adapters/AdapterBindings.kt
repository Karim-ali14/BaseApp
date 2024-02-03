package com.karimali.baseapp.ui.adapters

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.gone
import com.karimali.baseapp.common.extensions.visible
import com.karimali.baseapp.databinding.*
import com.karimali.baseapp.date.models.home.BannerModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.ProductCategoryModel
import com.karimali.baseapp.date.models.home.ProductModel
import com.karimali.baseapp.date.models.home.ServiceModel
import java.util.*
import kotlin.math.roundToInt


typealias OnViewItemsPressed<T> = (View,T) -> Unit

////////////////////////////////////////////////////////////////
//////////////////// TextView Binding Adapter//////////////////
@BindingAdapter("showHtmlText")
fun TextView.showHtmlText(htmlText:String?) {
    if (!htmlText.isNullOrEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text =
                Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            this.text = Html.fromHtml(htmlText)
        }
    }
}
@BindingAdapter("bindTextColor")
fun TextView.bindTextColor(color:String?) {
    this.setTextColor(Color.parseColor(color))
}
////////////////////////////////////////////////////////////////
////// BindingAdapter ForCircularProgressButton enable ////////
@BindingAdapter("isEnable")
fun CircularProgressButton.isButtonEnable(isTrue:Boolean) {
    this.isEnabled = isTrue
    this.backgroundTintList = if (isTrue) ContextCompat.getColorStateList(context, R.color.orange_color) else ContextCompat.getColorStateList(context, R.color.hint_color)
    this.setTextColor(
        if (isTrue) ContextCompat.getColorStateList(context, R.color.white) else ContextCompat.getColorStateList(
            context,
            R.color.black
        )
    )
}

////////////////////////////////////////////////////////////////
/////////////// Handle show or hidden views ///////////////////
@BindingAdapter("isVisible")
fun View.isVisible(isTrue:Boolean) {
    visibility = if(isTrue) View.VISIBLE else View.GONE
}

@BindingAdapter("handleVisibility")
fun View.handleVisibility(show : Boolean) {
    if (show) this.visible() else this.gone()
}

////////////////////////////////////////////////////////////////
/////////////// Handle set margin to views ////////////////////
@BindingAdapter("marginHorizontalBinding")
fun setMarginHorizontal(view: View, marginHorizontal: Float) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(
        marginHorizontal.roundToInt(), layoutParams.topMargin,
        marginHorizontal.roundToInt(), layoutParams.bottomMargin
    )
    view.layoutParams = layoutParams
}
@BindingAdapter("marginVerticalBinding")
fun setMarginVertical(view: View, marginVertical: Float) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin, marginVertical.roundToInt(),
        layoutParams.rightMargin, marginVertical.roundToInt()
    )
    view.layoutParams = layoutParams
}

@BindingAdapter("marginTopBinding")
fun setMarginTop(view: View, marginVertical: Float) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin, marginVertical.roundToInt(),
        layoutParams.rightMargin, layoutParams.bottomMargin
    )
    view.layoutParams = layoutParams
}

@BindingAdapter("marginBottomBinding")
fun setMarginBottom(view: View, marginVertical: Float) {
    val layoutParams = view.layoutParams as MarginLayoutParams
    layoutParams.setMargins(
        layoutParams.leftMargin, layoutParams.topMargin,
        layoutParams.rightMargin, marginVertical.roundToInt()
    )
    view.layoutParams = layoutParams
}

////////////////////////////////////////////////////////////////
// BindingAdapter For Bind Any Generic Recycler View Adapter //

@BindingAdapter(value = ["recyclerAdapter","isLinear","cols","isHorizontal"], requireAll = false)
fun <T : Any> RecyclerView.setup(customAdapter: GenericRecyclerAdapter<T>, isLinear:Boolean = true, cols : Int? = 2, isHorizontal : Boolean = false){
    val orientation = if(isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    adapter = customAdapter
    layoutManager = if(isLinear) LinearLayoutManager(context,orientation,false) else GridLayoutManager(context,cols?:2)
}

@BindingAdapter(value = ["recyclerAdapter","isLinear","cols","isHorizontal"], requireAll = false)
fun <T : Any> RecyclerView.setup(customAdapter: RecyclerView.Adapter<*>, isLinear:Boolean = true, cols : Int? = 2, isHorizontal : Boolean = false){
    val orientation = if(isHorizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
    adapter = customAdapter
    layoutManager = if(isLinear) LinearLayoutManager(context,orientation,false) else GridLayoutManager(context,cols?:2)
}

////////////////////////////////////////////////////////////////
////////////////////// TextInputLayout ////////////////////////

@BindingAdapter(value = ["setFocusedMode"])
fun TextInputLayout.setFocusedMode(focusedMode:Boolean){
    this.editText?.isFocusable = focusedMode
    this.editText?.setTextColor(
        if (focusedMode)
            ContextCompat.getColor(this.context,R.color.black)
        else
            ContextCompat.getColor(this.context,R.color.hint_color)
    )
    if (!focusedMode)
        this.setEndIconOnClickListener {  }
}

////////////////////////////////////////////////////////////////
////////////////////// setBackground //////////////////////////
@BindingAdapter(value = ["setCardBackgroundColor"])
fun MaterialCardView.setCardBackgroundColor(cardColor:String){
    try {
        this.setCardBackgroundColor(Color.parseColor(cardColor))
    }catch (e:Exception){
        this.setCardBackgroundColor(ContextCompat.getColor(this.context,R.color.orange_color))
    }
}



@BindingAdapter(value = ["setBackgroundColor"])
fun View.setBackgroundColor(cardColor:String){
    try {
        this.setBackgroundColor(Color.parseColor(cardColor))
    }catch (e:Exception){
        this.setBackgroundColor(ContextCompat.getColor(this.context,R.color.orange_color))
    }
}

object AdapterBindings {

    fun<T> shimmerBinding() : GenericSimpleRecyclerBindingInterface<T> {
        return object : GenericSimpleRecyclerBindingInterface<T> {
            override fun bindData(item: T, view: View,position: Int?) {}
        }
    }

    fun bannerItemBinding() = object :GenericSimpleRecyclerBindingInterface<BannerModel>{
        override fun bindData(item: BannerModel, view: View,position: Int?) {
            DataBindingUtil.bind<ImageBannerItemLayoutBinding>(view)?.apply {
                bannerImg.setImageResource(item.image)
            }
        }
    }

    fun sectionItemBinding() = object :GenericSimpleRecyclerBindingInterface<CategoryModel>{
        override fun bindData(item: CategoryModel, view: View,position: Int?) {
            DataBindingUtil.bind<SectionItemLayoutBinding>(view)?.apply {
                sectionItem = item
            }
        }
    }

    fun productCategoryItemBinding() = object :GenericSimpleRecyclerBindingInterface<ProductCategoryModel>{
        override fun bindData(item: ProductCategoryModel, view: View,position: Int?) {
            DataBindingUtil.bind<SeeAllTitleItemLayoutBinding>(view)?.apply {
                this.title = item.name_en

                recycler.setup(
                    GenericRecyclerAdapter(
                        item.products,
                        R.layout.product_horizontal_item_layout,
                        productItemBinding()
                    ), isHorizontal = true
                )
            }
        }
    }

    fun productItemBinding() = object :GenericSimpleRecyclerBindingInterface<ProductModel>{
        override fun bindData(item: ProductModel, view: View, position: Int?) {
            DataBindingUtil.bind<ProductHorizontalItemLayoutBinding>(view)?.apply {
                productItem = item
            }
        }
    }

    fun serviceItemBinding() = object :GenericSimpleRecyclerBindingInterface<ServiceModel>{
        override fun bindData(item: ServiceModel, view: View, position: Int?) {
            DataBindingUtil.bind<ServiceHorizontalItemLayoutBinding>(view)?.apply {
                serviceItem = item
            }
        }
    }

//    fun optionSelectorItemAdapterOnPress(onPress: (SelectorItemWithTextAndIcon) -> Unit) = object :
//        OnPressedInterface<SelectorItemWithTextAndIcon> {
//        override fun onPressed(item: Any?) {
//            onPress.invoke(item as SelectorItemWithTextAndIcon)
//        }
//        override fun onPressedWithPos(item: Any?, pos: Int) {
//
//        }
//    }

}