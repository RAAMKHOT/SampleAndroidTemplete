package com.sample.templet.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.work.*
import com.sample.templet.BuildConfig
import com.sample.templet.database.appdata.AppDatabase
import com.sample.templet.utils.local.DataClass
import java.util.concurrent.TimeUnit

class ApplicationController : Application(), ActivityLifecycleCallbacks {
    private val TAG = ApplicationController::class.java.simpleName
    private val TAG_WORK_NAME = "Global_Zyter"
    private lateinit var workManagerInstance: WorkManager
    private var currentlyDisplayActivity: Activity? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: ApplicationController? = null
        private var isAppInBackgroundAt: Long? = null
        lateinit var appContext: Context
        private val APP_TIMEOUT_SEC = 10L // after 10 second app session will expire..
        var isCLIENT = false
        private const val DATABASE_NAME: String = "telehealth_db"
        private var database: AppDatabase? = null

        @Synchronized
        fun getInstance(): ApplicationController {
            return mInstance!!
        }

        fun isAppInBackgroundAt(): Long? {
            return isAppInBackgroundAt
        }

        fun setAppInBackgroundAt(currentTimeMillSec: Long) {
            /*val user = AppPreferences.getLoginUser()
            if(user != null) {
                isAppInBackgroundAt = currentTimeMillSec
            }*/
        }
        fun isBackgroundTimeOut(): Boolean {
            val tenAgo: Long = isAppInBackgroundAt!! + TimeUnit.SECONDS.toMillis(APP_TIMEOUT_SEC)
            return tenAgo < System.currentTimeMillis()
        }
    }

    fun getDB(): AppDatabase {
        return database!!
    }

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        mInstance = this
        appContext = applicationContext

        database = Room.databaseBuilder(applicationContext,AppDatabase::class.java,DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        getDB()

        workManagerInstance = WorkManager.getInstance(applicationContext)

        workManagerInstance.cancelAllWorkByTag(TAG_WORK_NAME)
        //periodicWorkRequest() // Work in progress in WorkManager functionality
        registerActivityLifecycleCallbacks(this)
    }


    fun getHeaders(user: String?, loginFlag: Boolean): Map<String?, String?> {
        var params: MutableMap<String?, String?> = HashMap()
        if (!loginFlag) {
            //params["X-User-Key"] = ""
            /*if (DataClass.getInstance() != null && DataClass.getInstance()
                    ?.getmEccKey() != null && DataClass.getInstance()
                    ?.getmEccKey()?.exchangeId != null
            ) {
                //params["X-Exchange-Id"] = DataClass.getInstance()?.getmEccKey()?.exchangeId.toString()
            }*/
            params["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8"
            //params["Content-Type"] = "application/x-www-form-urlencoded"
        } else {
            params["Content-Type"] = "application/json; charset=utf-8"
        }
        params = headerCommonValue(params, user)
        return params
    }

    private fun headerCommonValue(params: MutableMap<String?, String?>, user: String?): MutableMap<String?, String?> {
        params["X-Application-Access-Key"] = BuildConfig.ACCESSKEY
        if (DataClass.getInstance() != null && DataClass.getInstance()?.getSchemaName() != null && DataClass.getInstance()?.getSchemaName()?.isNotEmpty() == true) {
            params["schemaName"] = DataClass.getInstance()?.getSchemaName()
        } else {
            val schema = if (user!!.contains("\\")) user.replace("\\", "#").split("#".toRegex()).toTypedArray()[0] else ""
            params["schemaName"] = schema
            DataClass.getInstance()?.setSchemaName(schema)
        }
        val userId = if (user!!.contains("\\")) user.replace("\\", "#").split("#".toRegex())
            .toTypedArray()[1] else user
        params["X-User-Id"] = userId
        /*if (DataClass.getInstance() != null && DataClass.getInstance()?.getLoginUser() != null) {
            params["X-Auth-Token"] = DataClass.getInstance()?.getLoginUser()?.authToken
        }*/
        return params
    }





    //-----------* START *------* Register Activity Lifecycle Callbacks *--------------
    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        Log.e("Application Controller", "onActivityCreated ->" + activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.e("Application Controller", "onActivityStarted ->" + activity.localClassName)
        //isAppInBackgroundAt = null
    }

    override fun onActivityResumed(activity: Activity) {
        Log.e("Application Controller", "onActivityResumed ->" + activity.localClassName)
        /*val user = AppPreferences.getLoginUser()
        if (activity !is ConfirmPinActivity && user != null) {
            currentlyDisplayActivity = activity
        }*/
    }

    override fun onActivityPaused(activity: Activity) {
        //Log.e("Application Controller", "onActivityPaused ->" + activity.localClassName)
    }

    override fun onActivityStopped(activity: Activity) {
        Log.e("Application Controller", "onActivityStopped ->" + activity.localClassName)
        if (activity == currentlyDisplayActivity) {
            setAppInBackgroundAt(System.currentTimeMillis())
        } else {
            isAppInBackgroundAt = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
        //Log.e("Application Controller", "onActivitySaveInstanceState ->" + activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.e("Application Controller", "onActivityDestroyed ->" + activity.localClassName)
        isAppInBackgroundAt = null
    }
    //----------* END *------* Register Activity Lifecycle Callbacks *--------------
}