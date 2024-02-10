package com.karimali.baseapp.date.apis


import com.karimali.baseapp.common.utils.Route
import com.karimali.baseapp.date.models.ClientModel
import com.karimali.baseapp.date.models.ResponseModel
import com.karimali.baseapp.date.models.home.CategoryModel
import com.karimali.baseapp.date.models.home.HomeDateModel
import com.karimali.baseapp.date.models.home.ProductModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface Services {

    // Auth apis //

    @FormUrlEncoded
    @POST(Route.LOGIN)
    suspend fun login(
        @Field("phone") phone:String ,
        @Field("password") password:String ,
    ):ResponseModel<ClientModel>

    @Multipart
    @POST(Route.REGISTRATION)
    suspend fun registration(
        @PartMap body: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part
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


    // home apis //
    @GET(Route.HOME)
    suspend fun fetchHomeDate():ResponseModel<HomeDateModel>

    @GET(Route.SHOW_ALL_CATEGORIES)
    suspend fun fetchAllCategoryData():ResponseModel<ArrayList<CategoryModel>>
    @GET(Route.SHOW_ALL_CATEGORIES)
    suspend fun fetchAllProductData(
        @Query("tag_id") tagId:String
    ):ResponseModel<ArrayList<ProductModel>>

}
