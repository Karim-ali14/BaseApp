package com.karimali.baseapp.date.repositories.homeRepo

import com.karimali.baseapp.date.models.ResponseModel
import com.karimali.baseapp.date.models.home.HomeDateModel

interface HomeRepository {

    suspend fun fetchHomeDate(): ResponseModel<HomeDateModel>

}