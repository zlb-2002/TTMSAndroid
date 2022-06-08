package com.xupt.ttms.ui.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xupt.ttms.databinding.ActivityScheduleBinding
import com.xupt.ttms.util.adapter.ScheduleAdapter

class ScheduleActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]

        setSupportActionBar(binding.scheduleToolbar)
        binding.scheduleToolbar.setNavigationOnClickListener { finish() }

        intent.getStringExtra("movieId")?.let { scheduleViewModel.setMovieId(it) }

        val adapter = scheduleViewModel.schedule.value?.let { ScheduleAdapter(it) }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        Log.d("TAG", "onCreate: ")
        scheduleViewModel.getList()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scheduleViewModel.getList()
                }
            }
        })

        scheduleViewModel.schedule.observe(this) {
            adapter?.notifyDataSetChanged()
        }
    }
}