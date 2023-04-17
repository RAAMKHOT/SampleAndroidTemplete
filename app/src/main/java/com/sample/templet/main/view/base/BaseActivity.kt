package com.sample.templet.main.view.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sample.templet.main.view.activities.LoginActivity
import com.sample.templet.utils.local.ThemeHelper
import com.sample.templet.utils.AppPreferences
import com.sample.templet.utils.ApplicationController

open class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        if (ApplicationController.isAppInBackgroundAt() != null &&
            ApplicationController.isBackgroundTimeOut()
        ) {
            val bundle = Bundle()
            bundle.putString(AppPreferences.PIN, AppPreferences.getString(AppPreferences.PIN, ""))
            bundle.putBoolean(AppPreferences.IS_PASSCODE, false)
            bundle.putBoolean(AppPreferences.IS_TIME_OUT, true)
            intent = Intent(this, LoginActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        } else {
            ApplicationController.setAppInBackgroundAt(System.currentTimeMillis())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyPermissions(this)
        window.statusBarColor = ThemeHelper.getInstance()?.getCurrentTheme(this)!!
    }

    private val PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.INTERNET,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val REQUEST_ALL_FILES_ACCESS_PERMISSION = 3


    open fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun verifyPermissions(activity: Activity) {
        // Check if we have write permission
        if (!hasPermissions(
                this,
                *PERMISSIONS
            )
        ) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                REQUEST_EXTERNAL_STORAGE
            )
        }
        if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //requestAllFileAccessParmission(activity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestAllFileAccessParmission(activity: Activity) {
        if (!Environment.isExternalStorageManager()) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s",
                        activity.applicationContext.packageName
                    )
                )
                activity.startActivityForResult(
                    intent,
                    REQUEST_ALL_FILES_ACCESS_PERMISSION
                )
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                activity.startActivityForResult(
                    intent,
                    REQUEST_ALL_FILES_ACCESS_PERMISSION
                )
            }
        }
    }


}