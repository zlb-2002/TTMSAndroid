package com.xupt.ttms.data.bean.scheduleBean


import com.google.gson.annotations.SerializedName

data class BuyTicketResponse(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)