package com.karimali.baseapp.common.extensions

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion


fun EditText.openKeyboard(){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.closeKeyboard(activity: Activity){
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        activity.currentFocus?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun TextInputLayout.getValue() = this.editText?.text?.toString() ?: ""
fun EditText.getValue() = this.text?.toString() ?: ""

fun TextInputLayout.setValue(value:String) {
    this.editText?.setText(value)
    Log.i("bindProviderDate","$value")
}
fun EditText.setValue(value:String) {
    this.setText(value)
    Log.i("bindProviderDate","$value")
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }.onCompletion { emit(text) }
}

fun Activity.closeKeyboard(){
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        this.currentFocus?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}
fun Activity.openKeyboard(view:EditText){
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);

}
