package com.sample.templet.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sample.templet.network.api.ApiHelper
import com.sample.templet.utils.ApplicationController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {

    @OptIn(DelicateCoroutinesApi::class)
    fun getHeadlines(country: String, category: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = withContext(Dispatchers.IO) { ApiHelper.topHeadlines(country, category) }
            ApplicationController.getInstance().getDB().articlesDao().insertAll(response.body()?.articles)
        }
    }
}