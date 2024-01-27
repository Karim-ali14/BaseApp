package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCategoryModel(
    val created_at: String,
    val id: Int,
    val image: String,
    val name_ar: String,
    val name_en: String,
    val name: String,
    val products: List<ProductModel>,
    val updated_at: String
):Parcelable