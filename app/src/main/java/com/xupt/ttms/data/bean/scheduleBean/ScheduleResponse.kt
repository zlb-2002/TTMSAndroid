package com.xupt.ttms.data.bean.scheduleBean


import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)