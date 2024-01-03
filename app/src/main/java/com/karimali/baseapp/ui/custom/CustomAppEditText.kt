package com.karimali.baseapp.ui.custom

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.karimali.baseapp.R
import com.karimali.baseapp.common.utils.Enums
import com.karimali.baseapp.databinding.CustomAppEditTextBinding

class CustomAppEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var binding:CustomAppEditTextBinding

    private var inputType : String = ""
        set(value) {
            field = value
            handleInputType()
            invalidate()
            requestLayout()
        }

    private var showPassword :Boolean = false
        set(value) {
            field = value
            handlePasswordStatus()

            invalidate()
            requestLayout()
        }

    private var errorMessage:String? = null
        set(value){
            field = value
            handleErrorMessage()
            invalidate()
            requestLayout()
        }

    init {
        binding = CustomAppEditTextBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomAppEditText,defStyleAttr,
            0
        ).apply {
            try {
                inputType = getString(R.styleable.CustomAppEditText_type) ?:
                        Enums.EditTextInputType.Text.name

                getString(R.styleable.CustomAppEditText_placeholderText)?.let { placeHolder ->
                    binding.placeHolder = placeHolder
                }
                getString(R.styleable.CustomAppEditText_hint)?.let { hint ->
                    binding.hintText = hint
                }

            }catch (e:Exception){
                Log.i("SDFASDFASFASDFS",e.message.toString())
            }finally {
                recycle()
            }

        }

        invalidate()
        requestLayout()
    }

    private fun handleErrorMessage() {
        binding.apply {
            this.errorMessage = this@CustomAppEditText.errorMessage ?: ""
            showError = this@CustomAppEditText.errorMessage != null
            handleShowRedStroke(this@CustomAppEditText.errorMessage != null)
        }
    }

    private fun handleShowRedStroke(showError: Boolean) {
        binding.inputCard.strokeColor =
            if (showError)
                ContextCompat.getColor(context, R.color.red)
            else {
                ContextCompat.getColor(context, R.color.edit_text_background)
            }
    }

    private fun handleInputType() {
        Log.i("TypeOfInput",inputType)
        when(inputType){
            Enums.EditTextInputType.Text.name -> {
                binding.input.inputType = InputType.TYPE_CLASS_TEXT
            }
            Enums.EditTextInputType.Phone.name -> {
                binding.input.inputType = InputType.TYPE_CLASS_NUMBER
            }
            Enums.EditTextInputType.Password.name -> {
                handlePasswordStatus()
            }
        }
    }

    private fun handlePasswordStatus() {
        Log.i("TypeOfInput",showPassword.toString())

//        if (showPassword){
//            binding.input.inputType = InputType.TYPE_CLASS_TEXT
//            binding.input.setCompoundDrawablesRelative(
//                null,
//                null,
//                AppCompatResources.getDrawable(context,R.drawable.password_eye_hide_ic),
//                null
//            )
//        }else {
        binding.input.inputType = InputType.TYPE_CLASS_TEXT and  InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.input.setCompoundDrawablesRelative(
                null,
                null,
                AppCompatResources.getDrawable(context,R.drawable.password_eye_unhide_ic),
                null
            )
//        }
        invalidate()
        requestLayout()
    }


}