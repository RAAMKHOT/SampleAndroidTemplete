package com.sample.templet.network.api

import com.sample.templet.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {
    private val retrofitClient: Retrofit.Builder by lazy {

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        builder.addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiService by lazy {
        retrofitClient.build().create(ApiService::class.java)
    }
}