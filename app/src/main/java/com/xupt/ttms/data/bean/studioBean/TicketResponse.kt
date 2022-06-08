package com.xupt.ttms.data.bean.studioBean


import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)