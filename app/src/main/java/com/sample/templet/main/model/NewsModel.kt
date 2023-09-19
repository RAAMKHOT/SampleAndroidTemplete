package com.sample.templet.main.model

import com.google.gson.annotations.SerializedName


data class NewsModel(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<ArticlesModel> = arrayListOf()
)