package com.xupt.ttms.data.source

import android.util.Log
import com.google.gson.Gson
import com.xupt.ttms.api.movie.ScheduleService
import com.xupt.ttms.data.bean.scheduleBean.BuyTicketRequest
import com.xupt.ttms.data.bean.scheduleBean.BuyTicketResponse
import com.xupt.ttms.data.bean.scheduleBean.Schedule
import com.xupt.ttms.data.bean.studioBean.Ticket
import com.xupt.ttms.data.bean.studioBean.TicketResponse
import com.xupt.ttms.util.retrofit.RetrofitManager
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ScheduleSource {

    private val scheduleService = RetrofitManager.createService(ScheduleService::class.java)
    private val gson = Gson()

    suspend fun getList(map:HashMap<String,String>):MutableList<Schedule> = try {
        scheduleService.getList(map).data.schedule.toMutableList()
    } catch (e:Exception) {
        e.printStackTrace()
        Log.d("TAG", "getList:$e")
        mutableListOf()
    }

    suspend fun getSeat(scheduleId:String):MutableList<Ticket> = try {
        scheduleService.getSeat(scheduleId).data.tickets.toMutableList()
    } catch (e:Exception) {
        Log.d("TAG", "getSeat: $e")
        mutableListOf()
    }

    suspend fun buyTicket(buyTicketRequest: BuyTicketRequest): BuyTicketResponse? = try {
        scheduleService.buyTicket(
            gson.toJson(buyTicketRequest).toRequestBody("application/json".toMediaTypeOrNull())
        )
    } catch (e:Exception) {
        Log.d("TAG", "buyTicket: $e")
        null
    }

}