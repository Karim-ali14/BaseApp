package com.karimali.baseapp.date.models.home

data class HomeDateModel(
    val categories: List<CategoryModel>,
    val products: List<ProductCategoryModel>,
    val services: List<Any>
)