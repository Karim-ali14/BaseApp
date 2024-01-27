package com.karimali.baseapp.date.repositories.homeRepo

import com.karimali.baseapp.date.apis.Services
import com.karimali.baseapp.date.models.ResponseModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import javax.annotation.Nullable

class HomeRepositoryImp(@Nullable val services: Services?):HomeRepository {
    override suspend fun fetchHomeDate(): ResponseModel<HomeDateModel> {
        return services!!.fetchHomeDate()
    }
}