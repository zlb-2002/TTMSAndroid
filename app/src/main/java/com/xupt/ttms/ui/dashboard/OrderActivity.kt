package com.xupt.ttms.ui.dashboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.xupt.ttms.data.bean.order.Data
import com.xupt.ttms.data.bean.order.Ticket
import com.xupt.ttms.databinding.OrderInformationBinding
import com.xupt.ttms.util.tool.ToastUtil
import java.text.DateFormat
import java.util.*

class OrderActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = OrderInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        binding.materialToolbar.setNavigationOnClickListener { finish() }

        val orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.order.observe(this){
            Log.d("TAG", "onCreate: $it")
            binding.materialToolbar.title = "详细订单"
            binding.orderId.text="订单号:${it.orderId}"
            binding.payTime.text = "支付时间:${DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,
                Locale.CHINA).format(it.payTime)}"
            binding.price.text = "${it.price}元"
            binding.descriptionNavigation.menu.apply {
                getItem(0).title = "数量: ${it.tickets?.size}"
                getItem(1).title = "电影名称: ${it.movieName}"
                getItem(2).title = "演出厅: ${it.studioName}"
                getItem(3).title = "开始时间: ${DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,
                    Locale.CHINA).format(it.startTime)}"
                getItem(4).title = "结束时间: ${DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,
                    Locale.CHINA).format(it.endTime)}"
            }
            binding.seat.text = it.tickets?.let { it1 -> getSeat(it1) }
            binding.orderStatus.text = when(it.orderStatus) {
                1 -> "有效"
                -1 -> {
                    binding.returnButton.visibility = View.GONE
                    "退款"
                }
                0 -> {
                    binding.returnButton.visibility = View.GONE
                    "无效"
                }
                else -> {
                    binding.returnButton.visibility = View.GONE
                    ""
                }
            }
        }
        intent.getParcelableExtra<Data>("data")?.let { orderViewModel.setData(it) }
        binding.returnButton.setOnClickListener {
            orderViewModel.reverseOrder()
        }

        orderViewModel.reverseOrder.observe(this) {
            ToastUtil.getToast(this, it.msg)
            if (it.data) {
                finish()
            }
        }

    }

    private fun getSeat(list: ArrayList<Ticket>):String{
        var string = ""
        for (ticket in list) {
            string += "${ticket.row}行${ticket.col}列\n"
        }
        return string
    }

}