package com.xupt.ttms.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.xupt.ttms.MainActivity
import com.xupt.ttms.R
import com.xupt.ttms.databinding.ActivityTokenBinding
import com.xupt.ttms.util.retrofit.RetrofitManager

class TokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tokenViewModel = ViewModelProvider(this)[TokenViewModel::class.java]
        tokenViewModel.isLogin.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}