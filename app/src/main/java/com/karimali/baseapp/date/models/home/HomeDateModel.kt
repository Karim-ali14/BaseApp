package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeDateModel(
    val categories: List<CategoryModel>,
    val products: List<ProductCategoryModel>,
    val services: List<ServiceModel>
):Parcelable