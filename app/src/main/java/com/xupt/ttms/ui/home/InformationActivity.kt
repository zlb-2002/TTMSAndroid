package com.xupt.ttms.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.xupt.ttms.R
import com.xupt.ttms.data.bean.movieBean.DataSource
import com.xupt.ttms.databinding.ActivityInformationBinding
import com.xupt.ttms.util.tool.ListUtil.myString
import java.text.DateFormat
import java.util.*

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val informationViewModel = ViewModelProvider(this)[InformationViewModel::class.java]

        setSupportActionBar(binding.infoToolbar)
        binding.infoToolbar.setNavigationOnClickListener { finish() }

        intent.getParcelableExtra<DataSource>("dataSource")
            ?.let { informationViewModel.getDataSource(it) }

        informationViewModel.dataSource.observe(this) {
            it ?:return@observe

            binding.infoToolbar.title = it.title
            binding.infoName.text = it.title
            binding.infoArea.text = "国家：${it.area?.myString()}"
            binding.infoActor.text = "演员：${it.actor?.myString()}"
            binding.infoType.text = it.type?.myString()
            binding.infoLanguage.text = "语言：${it.language?.myString()}"
            binding.infoLen.text = "${it.filmlen.toString()}分钟"
            binding.infoDate.text = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA).format(it.releaseDate)
            binding.infoIntroduction.text = "介绍：${it.introduction}"
            binding.infoPrice.text = it.rate.toString()

            binding.imageView2.load(it.cover) {
                error(R.drawable.error)
            }

            binding.buyTicket.setOnClickListener { _ ->
                startActivity(Intent(this, ScheduleActivity::class.java).putExtra("movieId", it.mid.toString()))
            }
        }
    }
}