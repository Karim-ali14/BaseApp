package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PivotModel(
    val product_id: String,
    val tag_id: String
):Parcelable