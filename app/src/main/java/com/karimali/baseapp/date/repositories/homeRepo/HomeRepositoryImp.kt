package com.karimali.baseapp.date.repositories.homeRepo

import com.karimali.baseapp.date.apis.Services
import com.karimali.baseapp.date.models.ResponseModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import com.karimali.baseapp.date.models.home.ProductModel
import javax.annotation.Nullable

class HomeRepositoryImp(@Nullable val services: Services?):HomeRepository {
    override suspend fun fetchHomeDate(): ResponseModel<HomeDateModel> {
        return services!!.fetchHomeDate()
    }

    override suspend fun fetchAllCategoryData(): ResponseModel<ArrayList<CategoryModel>> {
        return services!!.fetchAllCategoryData()
    }

    override suspend fun fetchAllProductData(tagId: String): ResponseModel<ArrayList<ProductModel>> {
        return services!!.fetchAllProductData(tagId)
    }
}