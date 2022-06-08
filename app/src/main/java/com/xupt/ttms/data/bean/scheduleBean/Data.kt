package com.xupt.ttms.data.bean.scheduleBean


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("schedule")
    val schedule: List<Schedule>,
    @SerializedName("sum")
    val sum: Int
)