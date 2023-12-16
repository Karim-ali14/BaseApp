package com.karimali.baseapp.di

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.karimali.baseapp.common.utils.Constants.Keys.USER_KEY
import com.karimali.baseapp.common.utils.Constants.Route.BASE_URL
import com.karimali.baseapp.date.apis.Services
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.annotation.Nullable
import okhttp3.Interceptor.Chain
import okhttp3.internal.platform.Platform
import okio.IOException
import java.util.*


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideOkHttpClient(prefs: AppSharedPrefs): OkHttpClient? {
        val clientModel = prefs.getSavedData<String>(USER_KEY)
        val loggingInterceptor = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.WARN)
            .request(" |==Req==|  ")
            .response(" |==Response==|  ")
            .addQueryParam("query", "0")
            .build()

        val authInterceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Chain): Response {
                val newRequest = chain.request().newBuilder()
                newRequest.addHeader("locale",Locale.getDefault().toString())
                newRequest.addHeader("Accept","application/json")
                Log.d("UserAuth", "${clientModel}")
                clientModel?.let {
//                    Log.d("UserAuth", "${it.token} ")
//                    newRequest.addHeader("Authorization", "Bearer ${it.token}")
                }
                return chain.proceed(newRequest.build())
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun buildRetrofit(@Nullable okHttpClient: OkHttpClient?): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(@Nullable retrofit: Retrofit?): Services? {
        return retrofit!!.create(Services::class.java)
    }

}