package com.karimali.baseapp.date.models.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ServiceModel(
    val created_at: String?= null,
    val id: Int? = 0,
    val image: String? = null,
    val name: String?= null,
    val description: String?= null,
    val price: String?= null,
    val name_ar: String?= null,
    val name_en: String?= null,
    val updated_at: String?= null
) : Parcelable {
    constructor() :this("")
}