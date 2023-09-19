package com.sample.templet.network.api

object ApiHelper {
    private val retrofitService = RetrofitService.apiInterface

    suspend fun topHeadlines(country: String, category: String) =
        retrofitService.topHeadlines(country, category)
}