package com.karimali.baseapp.date.repositories.homeRepo

import com.karimali.baseapp.date.models.ResponseModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import com.karimali.baseapp.date.models.home.ProductModel

interface HomeRepository {

    suspend fun fetchHomeDate(): ResponseModel<HomeDateModel>
    suspend fun fetchAllCategoryData():ResponseModel<ArrayList<CategoryModel>>
    suspend fun fetchAllProductData(
        tagId:String
    ):ResponseModel<ArrayList<ProductModel>>
}