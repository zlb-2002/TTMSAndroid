package com.xupt.ttms.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xupt.ttms.data.bean.scheduleBean.BuyTicketRequest
import com.xupt.ttms.data.bean.scheduleBean.BuyTicketResponse
import com.xupt.ttms.data.bean.scheduleBean.Schedule
import com.xupt.ttms.data.bean.studioBean.Ticket
import com.xupt.ttms.data.source.ScheduleSource
import com.xupt.ttms.util.tool.SeatUtil
import kotlinx.coroutines.launch

class SeatViewModel :ViewModel() {

    private val _seat = MutableLiveData<MutableList<Ticket>>()
    val seat:LiveData<MutableList<Ticket>> = _seat

    private val _schedule = MutableLiveData<Schedule>()
    val schedule:LiveData<Schedule> = _schedule

    private val _ticketRequest by lazy { MutableLiveData<BuyTicketRequest>() }
    val ticketRequest:LiveData<BuyTicketRequest>
        get() = _ticketRequest

    private val _ticketPrice by lazy { MutableLiveData(0) }
    val ticketPrice:LiveData<Int>
        get() = _ticketPrice

    private val scheduleSource = ScheduleSource()

    var firstTicketId = 0
    var row:Int = 0
    var col:Int = 0

    private val seatMap by lazy { MutableLiveData<MutableMap<Int, Ticket>>() }

    private val checkList by lazy { MutableLiveData<MutableList<Ticket>>(mutableListOf()) }

    private val _ticketResponse by lazy { MutableLiveData<BuyTicketResponse>() }
    val ticketResponse:LiveData<BuyTicketResponse>
        get() = _ticketResponse

    fun getSchedule(schedule: Schedule) {
        _schedule.value = schedule
    }

    fun getSeat() = viewModelScope.launch {
        _seat.value = scheduleSource.getSeat(schedule.value?.scheduleId.toString())
    }

    fun setSeatMap(list:MutableList<Ticket>) {
        seatMap.value = SeatUtil.listToMap(list)
    }

    fun append(row:Int, col:Int) {
        val id = getId(row, col)
        checkList.value = seatMap.value?.let { checkList.value?.let { it1 -> SeatUtil.appendToList(it, id, it1) } }
        _ticketPrice.value = ticketPrice.value !!+ schedule.value!!.ticketPrice
    }

    fun remove(row:Int, col:Int) {
        val id = getId(row, col)
        checkList.value = seatMap.value?.let { checkList.value?.let { it1 -> SeatUtil.removeToList(it, id, it1) }  }
        _ticketPrice.value = ticketPrice.value !!- schedule.value!!.ticketPrice
    }

    fun getTicket(phone:String) {
        val list = mutableListOf<Int>()
        checkList.value?.forEach { list.add(it.ticketId) }
        val ticket = schedule.value?.scheduleId?.let {
            BuyTicketRequest( list.toList(),
                it, phone)
        }
        _ticketRequest.value = ticket
    }

    fun getSeatValid(row:Int, col:Int): Ticket? = seatMap.value?.get(getId(row, col))

    private fun getId(row:Int, col:Int): Int = firstTicketId + (row-1)*this.col + col - 1

    fun buyTicket() = viewModelScope.launch {
        _ticketResponse.value = ticketRequest.value?.let { scheduleSource.buyTicket(it) }
    }



}