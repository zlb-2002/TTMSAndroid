package com.xupt.ttms.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.xupt.ttms.MainActivity
import com.xupt.ttms.data.bean.scheduleBean.Schedule
import com.xupt.ttms.databinding.ActivitySeatBinding
import com.xupt.ttms.util.tool.ToastUtil
import com.xupt.ttms.util.view.SeatTable

class SeatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.seatToolbar)
        binding.seatToolbar.setNavigationOnClickListener { finish() }

        val seatViewModel = ViewModelProvider(this)[SeatViewModel::class.java]

        intent.getParcelableExtra<Schedule>("schedule")?.let { seatViewModel.getSchedule(it) }

        binding.commitButton.apply {
            setOnClickListener {
                seatViewModel.getTicket()
            }
        }

        seatViewModel.ticketRequest.observe(this) {
            seatViewModel.buyTicket()
        }

        val seatTable = binding.seatTable

        seatTable.setScreenName(seatViewModel.schedule.value?.studioName)

        seatViewModel.getSeat()

        seatViewModel.seat.observe(this) {
            Log.d("TAG", "onCreate: $it")
            seatViewModel.firstTicketId = it[0].ticketId
            seatViewModel.setSeatMap(it)
            seatViewModel.row = it[it.size-1].row
            seatViewModel.col = it[it.size-1].col
            seatTable.setData(seatViewModel.row, seatViewModel.col)
            seatTable.setSeatChecker(object : SeatTable.SeatChecker{
                override fun isValidSeat(row: Int, column: Int): Boolean {
                    return true
                }

                override fun isSold(row: Int, column: Int): Boolean {
                    val data = seatViewModel.getSeatValid(row+1, column+1)
                    return data?.ticketStatus == 2 || data?.ticketStatus == 3
                }

                override fun checked(row: Int, column: Int) {
                    Log.d("TAG", "checked: $row+$column")
                    seatViewModel.append(row+1, column+1)
                }

                override fun unCheck(row: Int, column: Int) {
                    seatViewModel.remove(column+1, row+1)
                }

                override fun checkedSeatTxt(row: Int, column: Int): Array<String> {
                    return arrayOf("")
                }
            })
        }

        seatViewModel.ticketResponse.observe(this) {
            Log.d("TAG", "onCreate: $it")
            if (it.data) {
                ToastUtil.getToast(this, it.msg)
                startActivity(Intent(this@SeatActivity, MainActivity::class.java))
            } else {
                ToastUtil.getToast(this, it.msg)
            }
        }

        seatViewModel.ticketPrice.observe(this) {
            binding.ticketPrice.text = "${it}元"
        }

    }
}