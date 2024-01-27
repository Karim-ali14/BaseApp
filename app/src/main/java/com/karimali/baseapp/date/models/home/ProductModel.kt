package com.karimali.baseapp.date.models.home

data class ProductModel(
    val category_id: String,
    val created_at: String,
    val description_ar: String,
    val description_en: String,
    val discount: String,
    val id: Int,
    val image: String,
    val name_ar: String,
    val name_en: String,
    val number: String,
    val pivot: PivotModel,
    val price: String,
    val status: String,
    val updated_at: String
)