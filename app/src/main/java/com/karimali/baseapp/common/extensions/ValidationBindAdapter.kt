package com.karimali.baseapp.common.extensions

import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.karimali.baseapp.R
import com.karimali.baseapp.ui.custom.CustomAppEditText

fun CustomAppEditText.isValidatePhone() : Boolean {
    var isValidate = true
    if (getEditText().text.toString().isEmpty()){
        errorMessage = context.getString(R.string.required)
        isValidate =false
    } else if (!Patterns.PHONE.matcher(getEditText().text.toString().trim()).matches()
        || getEditText().text.toString().trim().isAllZeros()){
        errorMessage = context.getString(R.string.invalid_phone)
        isValidate = false
    } else if(!(getEditText().text.toString().trim().length == 10
         || getEditText().text.toString().trim().length == 9)
    ) {
        errorMessage = context.getString(R.string.invalid_phone)
        isValidate = false
    }

    getEditText().doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty() && Patterns.PHONE.matcher(getEditText().text!!.toString()).matches()){
            errorMessage = null
            isValidate = true
        }
    }
    return isValidate
}

fun CustomAppEditText.isEmptyFieldValidation() : Boolean {
    var isValid = true

    if(getEditText().text.toString().isEmpty()) {
        errorMessage = context.getString(R.string.required)
        isValid = false
    }

    getEditText().doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            errorMessage = null
            isValid = true
        }
    }

    return isValid
}

fun CustomAppEditText.confirmPasswordValidation(passwordTextLayout : CustomAppEditText) : Boolean {
    var isValid : Boolean = true
    when{
        getEditText().text!!.isEmpty() -> {
            errorMessage = context.getString(R.string.required)
            isValid = false
        }
        passwordTextLayout.getEditText().text.toString() != getEditText().text.toString() -> {
            errorMessage = context.getString(R.string.not_match)
            passwordTextLayout.errorMessage = context.getString(R.string.not_match)
            isValid = false
        }
    }

    getEditText().doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            passwordTextLayout.errorMessage = null
            errorMessage = null
            isValid = true
        }
    }
    return isValid
}

fun EditText.isCodeVerificationFieldNotEmpty():Boolean{
    var isValidate = true
    if (this.text.toString().isEmpty()) {
        isValidate = false
        background =
            AppCompatResources.getDrawable(context, R.drawable.verification_error_edit_text_shape)
    }else{
        isValidate = true
        background =
            AppCompatResources.getDrawable(context, R.drawable.verification_edit_text_shape)
    }
    this.doAfterTextChanged {
        if (this.text.toString().isEmpty()) {
            isValidate = false
            background =
                AppCompatResources.getDrawable(context, R.drawable.verification_error_edit_text_shape)
        }else{
            isValidate = true
            background =
                AppCompatResources.getDrawable(context, R.drawable.verification_edit_text_shape)
        }
    }

    return isValidate
}
