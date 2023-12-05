package com.karimali.baseapp.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.karimali.baseapp.R

class LoadingDialog : DialogFragment(R.layout.loading_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 20)
        requireDialog().window!!.apply {
            attributes.width = (ViewGroup.LayoutParams.MATCH_PARENT)
            attributes.height = (ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(inset)
        }

        requireDialog().setCancelable(false)
    }

    override fun isCancelable(): Boolean = false

    companion object{
        const val TAG = "LOADING"
    }
}