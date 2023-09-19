package com.sample.templet.main.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import com.sample.templet.R
import com.sample.templet.main.view.base.BaseActivity
import com.sample.templet.main.viewmodel.LoginViewModel
import com.sample.templet.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity(), View.OnClickListener  {
    private val TAG = LoginActivity::class.java.simpleName
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.imageViewPasswordVisible.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewPasswordVisible -> {
                onChangePasswordVisibility()
            }
            R.id.buttonLogin -> {
                viewModel.getHeadlines("us","business")
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onChangePasswordVisibility() {
        val cursorPosition: Int = binding.editTextPassword.selectionStart
        if (binding.editTextPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            binding.imageViewPasswordVisible.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_eye_show,
                    null
                )
            )
            binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            binding.imageViewPasswordVisible.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_eye_hide,
                    null
                )
            )
            binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        // returns cursor to position
        binding.editTextPassword.setSelection(cursorPosition)
    }

}