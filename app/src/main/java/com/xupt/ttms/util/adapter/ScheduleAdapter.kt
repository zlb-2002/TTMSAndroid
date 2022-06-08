package com.xupt.ttms.util.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xupt.ttms.data.bean.scheduleBean.Schedule
import com.xupt.ttms.databinding.ScheduleItemBinding
import com.xupt.ttms.ui.home.SeatActivity
import com.xupt.ttms.util.retrofit.RetrofitManager.context
import java.text.DateFormat
import java.util.*

class ScheduleAdapter(private val list: MutableList<Schedule>): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    class ViewHolder(binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root) {
        val studioName = binding.scheduleStudio
        val movieName = binding.scheduleMovie
        val startTime = binding.scheduleStartTime
        val endTime = binding.scheduleEndTime
        val price = binding.schedulePrice
        val view = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = list[position]
        holder.studioName.text = schedule.studioName
        holder.movieName.text = schedule.movieName
        holder.startTime.text = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA).format(schedule.startTime)
        holder.endTime.text = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINA).format(schedule.endTime)
        holder.price.text = schedule.ticketPrice.toString()
        holder.view.setOnClickListener {
            context.startActivity(Intent(context, SeatActivity::class.java).putExtra("schedule", schedule))
        }
    }

    override fun getItemCount(): Int = list.size
}