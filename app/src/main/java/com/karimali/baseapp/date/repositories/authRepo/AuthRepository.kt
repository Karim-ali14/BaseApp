package com.karimali.baseapp.date.repositories.authRepo

import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel

interface AuthRepository {

    suspend fun login(
        phone:String,
        password:String
    ): ResponseModel<ClientModel>

    suspend fun registration(
        phone:String ,
        firstName:String ,
        lastName:String ,
        password:String ,
        confirmPassword:String ,
        code:String ,
    ):ResponseModel<ClientModel>


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