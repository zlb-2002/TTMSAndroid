package com.xupt.ttms.ui.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.xupt.ttms.R
import com.xupt.ttms.databinding.ActivityPayBinding
import com.xupt.ttms.util.tool.ToastUtil

class PayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val payViewModel = ViewModelProvider(this)[PayViewModel::class.java]

        payViewModel.getUserId(intent.getIntExtra("userId", 0))

        binding.back.setOnClickListener {
            finish()
        }

        binding.reCharge.setOnClickListener {
            payViewModel.pay(binding.money.text.toString())
        }

        payViewModel.payResponse.observe(this) {
            ToastUtil.getToast(this, it.msg)
            if (it.data) finish()
        }
    }
}