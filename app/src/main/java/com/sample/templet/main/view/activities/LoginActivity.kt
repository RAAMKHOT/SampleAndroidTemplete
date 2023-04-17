package com.sample.templet.main.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import com.sample.templet.main.view.base.BaseActivity
import com.sample.templet.main.viewmodel.LoginViewModel
import com.sample.templet.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

    }

}