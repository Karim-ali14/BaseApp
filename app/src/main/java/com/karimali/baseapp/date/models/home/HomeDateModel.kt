package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeDateModel(
    val categories: ArrayList<CategoryModel>,
    val products: ArrayList<ProductCategoryModel>,
    val services: ArrayList<ServiceModel>
):Parcelable