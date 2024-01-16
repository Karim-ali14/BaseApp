package com.karimali.baseapp.date.models

import com.google.gson.annotations.SerializedName

data class ResponseModel<T> (
    var status : Boolean,
    var message : String,
    var data : T,
    var code :Int,
    val errors : List<String>?
)

