package com.xupt.ttms.util.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xupt.ttms.data.bean.order.Data
import com.xupt.ttms.databinding.OrderItemBinding
import com.xupt.ttms.ui.dashboard.OrderActivity
import com.xupt.ttms.util.retrofit.RetrofitManager.context
import java.text.DateFormat
import java.util.*

class OrderAdapter(private val list:List<Data>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val orderId = binding.orderId
        val movieName = binding.movieName
        val startTime = binding.startTime
        val orderStatus = binding.orderStatus
        val view = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.orderId.text = "订单号：${data.orderId}"
        holder.movieName.text = "电影名称：${data.movieName}"
        holder.startTime.text = "开始时间：${DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, Locale.CHINA).format(data.startTime)}"
        holder.orderStatus.text = when (data.orderStatus) {
            -1 -> "退款"
            0 -> "无效"
            1 -> "有效"
            else -> ""
        }
        holder.view.setOnClickListener {
            context.startActivity(Intent(context, OrderActivity::class.java).putExtra("data",data))
        }
    }

    override fun getItemCount(): Int = list.size
}