package com.sample.templet.network.api

import com.sample.templet.main.model.ArticlesModel
import com.sample.templet.main.model.NewsModel
import com.sample.templet.network.api.APIConstant.API_KEY
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("top-headlines")
    suspend fun topHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsModel>
}