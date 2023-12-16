package com.karimali.baseapp.ui.adapters

import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.android.material.textfield.TextInputLayout
import com.karimali.baseapp.R
import com.karimali.baseapp.databinding.*
import java.util.*
import kotlin.math.roundToInt


typealias OnViewItemsPressed<T> = (View,T) -> Unit

//BindingAdapter For Bind Any Generic Recycler View Adapter
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

//BindingAdapter ForCircularProgressButton enable
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
//BindingAdapter For Bind Any Generic Recycler View Adapter
@BindingAdapter("isVisible")
fun View.isVisible(isTrue:Boolean) {
    visibility = if(isTrue) View.VISIBLE else View.GONE
}


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
object AdapterBindings {

    fun<T> shimmerBinding() : GenericSimpleRecyclerBindingInterface<T> {
        return object : GenericSimpleRecyclerBindingInterface<T> {
            override fun bindData(item: T, view: View,position: Int?) {}
        }
    }

//    fun bannerBinding() = object :GenericSimpleRecyclerBindingInterface<Banner>{
//        override fun bindData(item: Banner, view: View,position: Int?) {
//            DataBindingUtil.bind<ImageBannerItemLayoutBinding>(view)?.apply {
//                image.loadImage(item.imagePath)
//            }
//        }
//    }
//
//    fun imageBinding() = object :GenericSimpleRecyclerBindingInterface<Banner>{
//        override fun bindData(item: Banner, view: View,position: Int?) {
//            DataBindingUtil.bind<ImageItemLayoutBinding>(view)?.apply {
//                image.loadImage(item.imagePath)
//            }
//        }
//    }
//
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