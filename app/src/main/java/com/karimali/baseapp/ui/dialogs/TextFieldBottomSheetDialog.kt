package com.karimali.baseapp.ui.dialogs

import android.os.Bundle
import android.view.View
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.karimali.baseapp.R
import com.karimali.baseapp.common.extensions.gone
import com.karimali.baseapp.common.extensions.validation
import com.karimali.baseapp.common.extensions.visible
import com.karimali.baseapp.databinding.TextFieldBottomsheetLayoutBinding
import com.karimali.baseapp.ui.base.RoundedBottomSheetDialogFragment


class TextFieldBottomSheetDialog(private var initialContent:String ?= null, val onSelect : (String, CircularProgressButton) -> Unit, val header : String ?= null, val inputType: Int ?= null, val hint : String, val buttonText : String ?= null) : RoundedBottomSheetDialogFragment<TextFieldBottomsheetLayoutBinding>(
    R.layout.text_field_bottomsheet_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = TextFieldBottomsheetLayoutBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {

            if(header != null) {
                headerTxt.visible()
                headerTxt.text = header
            }else{
                headerTxt.gone()
            }

            degreeTl.hint = hint

            inputType?.let {
                degreeTl.editText?.inputType = it
            }

            buttonText?.let {
                saveBtn.text = it
            }

            saveBtn.setOnClickListener {
                if(degreeTl.validation()){
                    val text = degreeTl.editText?.text.toString()
                    onSelect(text,saveBtn)
                    dismiss()
                }
            }

            initialContent?.let {
                degreeTl.editText?.setText(it)
            }

        }

    }
}