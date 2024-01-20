package com.karimali.baseapp.common.extensions

import android.graphics.Color
import android.graphics.Paint
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karimali.baseapp.common.utils.SpaceItemDecoration

/////////////////////////////////////////////////////////////////////////
// ImageViews Adapters.
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["loadingImageUrl"])
fun ImageView.loadingImageUrl(url: String?) {
    this.run {

        if (!url.isNullOrEmpty()) {
            Glide
                    .with(this)
                    .load(url)
//                    .placeholder(R.drawable.ic_image_placeholder_48)
//                    .error(R.drawable.ic_image_placeholder_48)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .dontAnimate()
                    .dontTransform()
                    .priority(Priority.HIGH)
                    .into(this)
        } else {
//            this.setImageDrawable(
//                    ContextCompat.getDrawable(
//                            context,
//                            R.drawable.ic_image_placeholder_48
//                    )
//            )
        }
    }
}

/////////////////////////////////////////////////////////////////////////
// ImageViews SRC Adapters. , No Need To Use bind Prefix.
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["android:src"])
fun ImageView.setImageViewResource(resource: Int) {
    this.setImageResource(resource)
}

/////////////////////////////////////////////////////////////////////////
// RecyclerView Add Space Decoration
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["addSpaceDecoration"])
fun RecyclerView.addSpaceDecoration(space: Int) {
    this.addItemDecoration(SpaceItemDecoration(space))
}

/////////////////////////////////////////////////////////////////////////
// Swipe Refresh Binding Adapter.
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["onRefresh"])
fun SwipeRefreshLayout.onRefresh(action: () -> Unit) {
    this.setOnRefreshListener {
        action()
    }
}

/////////////////////////////////////////////////////////////////////////
// Card Adapter.
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["changeCardBackground"])
fun CardView.changeCardBackground(color: String?) {
    this.let {
        if (!color.isNullOrEmpty())
            setCardBackgroundColor(Color.parseColor(color))
//        else // Todo active this line after discuss with Eng.Amer
//            setCardBackgroundColor(Color.parseColor("#2e7d32"))
    }
}

/////////////////////////////////////////////////////////////////////////
// Swipe Adapter.
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["isRefreshing"])
fun SwipeRefreshLayout.isRefreshing(newValue: Boolean) {
    this.isRefreshing = newValue
}


/////////////////////////////////////////////////////////////////////////
// TextView .
/////////////////////////////////////////////////////////////////////////

@BindingAdapter(value = ["setLineUnderText"])
fun TextView.setLineUnderText(setLine:Boolean){
    if (setLine)
        this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}
@BindingAdapter(value = ["setLineAboveText"])
fun TextView.setLineAboveText(setLine:Boolean){
    if (setLine)
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}
