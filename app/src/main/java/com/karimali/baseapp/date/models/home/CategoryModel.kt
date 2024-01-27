package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CategoryModel(
    val color: String,
    val icon: String,
    val id: Int,
    val name_ar: String,
    val name_en: String,
    val name: String,
    val status: String
):Parcelable