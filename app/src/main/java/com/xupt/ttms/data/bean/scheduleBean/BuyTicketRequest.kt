package com.xupt.ttms.data.bean.scheduleBean


import com.google.gson.annotations.SerializedName

data class BuyTicketRequest(
    @SerializedName("tickets")
    val tickets: List<Int>,
    @SerializedName("scheduleId")
    val scheduleId: Int,
    @SerializedName("phone")
    val phone: String
)