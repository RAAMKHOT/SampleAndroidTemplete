package com.sample.templet.main.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.viewModels
import com.sample.templet.databinding.ActivitySplashBinding
import com.sample.templet.main.view.base.BaseActivity
import com.sample.templet.main.viewmodel.SplashViewModel
import com.sample.templet.utils.AppPreferences
import com.sample.templet.utils.AppPreferences.IS_FIRST_TIME_LAUNCH
import com.sample.templet.utils.AppPreferences.IS_PASSCODE
import com.sample.templet.utils.AppPreferences.PIN

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT = 2000
    private var isFirstTimeLunched: Boolean = false
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        isFirstTimeLunched = AppPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true)

        Handler().postDelayed(
            {
                showLoginPage()
            }, SPLASH_TIME_OUT.toLong()
        )
    }

    private fun showLoginPage() {
        AppPreferences.putString(PIN, "")
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }
    private fun showLandingPage() {
        val bundle = Bundle()
        bundle.putString(PIN, AppPreferences.getString(PIN,""))
        bundle.putBoolean(IS_PASSCODE, false)
        intent = Intent(this, LoginActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

}