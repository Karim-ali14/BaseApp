package com.karimali.baseapp.date.apis


import com.karimali.baseapp.common.utils.Constants.Route.CONFIRM_CODE
import com.karimali.baseapp.common.utils.Constants.Route.LOGIN
import com.karimali.baseapp.common.utils.Constants.Route.RESET_PASSWORD
import com.karimali.baseapp.common.utils.Constants.Route.SEND_CODE
import com.karimali.baseapp.common.utils.Constants.Route.SEND_CODE_FORGET_PASSWORD
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Services {

    // Auth apis //
    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field("phone") phone:String ,
        @Field("password") password:String ,
    ):ResponseModel<ClientModel>

    @FormUrlEncoded
    @POST(SEND_CODE)
    suspend fun sendCode(
        @Field("phone") phone:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(SEND_CODE_FORGET_PASSWORD)
    suspend fun sendCodeForForgetPassword(
        @Field("phone") phone:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(CONFIRM_CODE)
    suspend fun confirmCode(
        @Field("phone") phone:String ,
        @Field("code") code:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(RESET_PASSWORD)
    suspend fun resetPassword(
        @Field("phone") phone:String ,
        @Field("code") code:String ,
        @Field("password") password:String ,
        @Field("confirm-password") confirmPassword:String
    ):ResponseModel<Any>

}
