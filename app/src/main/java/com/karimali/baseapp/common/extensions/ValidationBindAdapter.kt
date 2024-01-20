package com.karimali.baseapp.common.extensions

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.karimali.baseapp.R
import com.karimali.baseapp.ui.custom.CustomAppEditText
import de.hdodenhof.circleimageview.CircleImageView
import java.util.regex.Pattern

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

fun CustomAppEditText.nameValidation(errorMsg : String = "Required") : Boolean {

    getEditText().doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            errorMessage = null
        }
    }

    if (TextUtils.isEmpty(getEditText().text.toString())) {
        errorMessage = context.getString(R.string.required)
        this.requestFocus()
        return false
    }else if (
        getEditText().text.toString().trim{ it <= ' ' }.contains("[0-9]".toRegex())
        ||
        getEditText()?.text.toString().trim{ it <= ' ' }.contains("(?=.*[@#\$%^&+=])".toRegex())

    ) {
        errorMessage = context.getString(R.string.name_validate_error)
        this.requestFocus()
        return false
    } else
        errorMessage = null

    return true
}

fun CustomAppEditText.isValidPassword(): Boolean {
    val regex = "^(?=.*[a-zA-Z]).{8,}\$"

    getEditText().doOnTextChanged { text, _ , _ , _ ->
        if(text!!.isNotEmpty()){
            errorMessage = null
        }
    }

    if (TextUtils.isEmpty(getEditText().text.toString())) {
        errorMessage = context.getString(R.string.required)
        this.requestFocus()
        return false
    }else if (!Pattern.compile(regex).matcher(getEditText().text.toString().trim{ it <= ' ' }).matches()) {
        errorMessage = context.getString(R.string.password_is_not_valid)
        this.requestFocus()
        return false
    } else
        errorMessage = null

    return true
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

fun String?.isEmptyIconValidation(icon: CircleImageView) : Boolean {
    val iconPath:String? = this
    var isValid : Boolean = true

    if(iconPath == null || iconPath.isEmpty()) {
        icon.borderColor = icon.context.resources.getColor(R.color.red)
        icon.borderWidth = 2
        isValid = false
    }else {
        icon.borderWidth = 0
        isValid = true
    }

    return isValid
}
