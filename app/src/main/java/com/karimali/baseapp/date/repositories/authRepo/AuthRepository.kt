package com.karimali.baseapp.date.repositories.authRepo

import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel
import retrofit2.http.Field

interface AuthRepository {

    suspend fun login(
        phone:String,
        password:String
    ): ResponseModel<ClientModel>

    suspend fun sendCode(
        phone:String
    ):ResponseModel<Any>

    suspend fun sendCodeForForgetPassword(
        phone:String
    ):ResponseModel<Any>

    suspend fun confirmCode(
        phone:String ,
        code:String
    ):ResponseModel<Any>

    suspend fun resetPassword(
        phone:String ,
        code:String ,
        password:String ,
        confirmPassword:String
    ):ResponseModel<Any>
}