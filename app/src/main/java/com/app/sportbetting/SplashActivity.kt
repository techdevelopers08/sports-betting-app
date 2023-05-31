package com.app.sportbetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.sportbetting.databinding.ActivitySplashBinding
import com.app.sportbetting.ui.auth.SignInActivity
import com.app.sportbetting.utils.SharedPreference
import com.app.sportbetting.utils.delayOnLifecycle



class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private lateinit var binding : ActivitySplashBinding
    var preference:SharedPreference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSplash()
    }

    private fun initSplash(){
        preference = SharedPreference.getInstance(this)
        binding.clLayout.delayOnLifecycle(2000L){
            if(preference?.getBoolean(SharedPreference.Key.ISUSERLOGIN,false) == true){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }

        }
    }

}