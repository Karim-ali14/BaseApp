package com.karimali.baseapp.date.apis


import com.karimali.baseapp.common.utils.Route
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Services {

    // Auth apis //
    @FormUrlEncoded
    @POST(Route.LOGIN)
    suspend fun login(
        @Field("phone") phone:String ,
        @Field("password") password:String ,
    ):ResponseModel<ClientModel>

    @FormUrlEncoded
    @POST(Route.REGISTRATION)
    suspend fun registration(
        @Field("phone") phone:String ,
        @Field("first_name") firstName:String ,
        @Field("last_name") lastName:String ,
        @Field("password") password:String ,
        @Field("confirm_password") confirmPassword:String ,
        @Field("code") code:String ,
    ):ResponseModel<ClientModel>

    @FormUrlEncoded
    @POST(Route.SEND_CODE)
    suspend fun sendCode(
        @Field("phone") phone:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(Route.SEND_CODE_FORGET_PASSWORD)
    suspend fun sendCodeForForgetPassword(
        @Field("phone") phone:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(Route.CONFIRM_CODE)
    suspend fun confirmCode(
        @Field("phone") phone:String ,
        @Field("code") code:String
    ):ResponseModel<Any>

    @FormUrlEncoded
    @POST(Route.RESET_PASSWORD)
    suspend fun resetPassword(
        @Field("phone") phone:String ,
        @Field("code") code:String ,
        @Field("password") password:String ,
        @Field("confirm-password") confirmPassword:String
    ):ResponseModel<Any>

}
