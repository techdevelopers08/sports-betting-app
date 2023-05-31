package com.app.sportbetting.ui.auth

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.sportbetting.network.Repository
import com.app.sportbetting.MainActivity
import com.app.sportbetting.databinding.ActivitySignInBinding
import com.app.sportbetting.models.login.LoginDetailsModel
import com.app.sportbetting.utils.*
import com.app.sportbetting.viewmodel.AuthViewModel
import com.app.sportbetting.viewmodel.ViewModelFactory
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignInBinding
    private var preference: SharedPreference? = null
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setObservers()
    }

    private fun setObservers() {
        viewModel.loginResult.observe(this, {
            it.let { data ->
                val message = data?.data?.message
                val body = data?.data?.body

                when (data.status) {
                    Status.SUCCESS -> {
                        MyApplication.hideLoader()
                        startActivity(Intent(this, MainActivity::class.java))
                        preference?.put(SharedPreference.Key.PASSWORD,binding.editTextTextPassword.text.toString())
                        preference?.put(SharedPreference.Key.TOKEN, body?.token)
                        preference?.put(SharedPreference.Key.USERID, body?.userDetails?.id!!)
                        preference?.put(SharedPreference.Key.ISUSERLOGIN, true)
                        preference?.put(SharedPreference.Key.EMAIL,body?.userDetails?.email)
                        preference?.put(SharedPreference.Key.USERNAME,body?.userDetails?.name)
                        finish()
                    }
                    Status.LOADING -> {
                        MyApplication.showLoader(this)
                    }
                    Status.ERROR -> {
                        MyApplication.hideLoader()
                        Toast.makeText(
                            this,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun checkValidation() {
        val body = LoginDetailsModel(
            binding.editTextTextEmailAddress.text.toString(),
            binding.editTextTextPassword.text.toString(),
        )

        if (Utility.isInternetConnected(this)) {
            if (isFieldEmpty()) {
                if (isEmailValid()) {
                    viewModel.onLogin(body)

                } else {
                    this.toast("Invalid Email")
                }
            } else {
                this.toast("All fields are mandatory to be filled")
            }
        } else {
            this.toast("Unable to connect to Internet")
        }
    }


    private fun isEmailValid(): Boolean = Pattern.matches(
        Patterns.EMAIL_ADDRESS.toRegex().toString(),
        binding.editTextTextEmailAddress.text.toString()
    )

    private fun isFieldEmpty(): Boolean {
        var isEmpty = true
        when {
            binding.editTextTextEmailAddress.text?.toString().isNullOrEmpty() -> {
                isEmpty = false

            }
            binding.editTextTextPassword.text?.toString().isNullOrEmpty() -> {
                isEmpty = false
                Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show()
            }
        }
        return isEmpty
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvSignUp -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            binding.btnSignIn -> {
                checkValidation()
            }
        }
    }


    private fun initViews() {
        binding.tvSignUp.setOnClickListener(this)
        binding.btnSignIn.setOnClickListener(this)
        preference= SharedPreference.getInstance(this)
        setupViewModel()
    }


    private fun setupViewModel() {
        val viewModelFactory =
            ViewModelFactory(Application(), repository = Repository())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)

    }

}