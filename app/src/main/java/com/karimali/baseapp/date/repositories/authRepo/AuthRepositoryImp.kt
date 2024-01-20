package com.karimali.baseapp.date.repositories.authRepo

import com.karimali.baseapp.common.extensions.toImagePart
import com.karimali.baseapp.date.apis.Services
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(private val services: Services?) :AuthRepository {
    override suspend fun login(phone: String, password: String): ResponseModel<ClientModel>
    = services!!.login(phone, password)

    override suspend fun registration(
        body: HashMap<String, RequestBody>,
        image: File
    ): ResponseModel<ClientModel> = services!!.registration(body,image.toImagePart("image"))


    override suspend fun sendCode(phone: String): ResponseModel<Any> = services!!.sendCode(phone)

    override suspend fun sendCodeForForgetPassword(phone: String): ResponseModel<Any>
    = services!!.sendCodeForForgetPassword(phone)

    override suspend fun confirmCode(phone: String, code: String): ResponseModel<Any>
    = services!!.confirmCode(phone, code)

    override suspend fun resetPassword(
        phone: String,
        code: String,
        password: String,
        confirmPassword: String
    ): ResponseModel<Any> = services!!.resetPassword(phone, code, password, confirmPassword)


}