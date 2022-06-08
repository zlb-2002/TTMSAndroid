package com.xupt.ttms.api.movie

import com.xupt.ttms.data.bean.scheduleBean.BuyTicketRequest
import com.xupt.ttms.data.bean.scheduleBean.BuyTicketResponse
import com.xupt.ttms.data.bean.scheduleBean.ScheduleResponse
import com.xupt.ttms.data.bean.studioBean.TicketResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ScheduleService {

    @GET("schedule/userQuery")
    suspend fun getList(@QueryMap map :HashMap<String, String>):ScheduleResponse

    @GET("ticket/getTicketsByScheduleId")
    suspend fun getSeat(@Query("scheduleId") studioId:String):TicketResponse

    @POST("user/buyTickets")
    suspend fun buyTicket(@Body body: RequestBody): BuyTicketResponse

}