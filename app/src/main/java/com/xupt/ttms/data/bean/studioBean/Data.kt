package com.xupt.ttms.data.bean.studioBean


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("colCount")
    val colCount: Int,
    @SerializedName("rowCount")
    val rowCount: Int,
    @SerializedName("tickets")
    val tickets: List<Ticket>
)