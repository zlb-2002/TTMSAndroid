package com.xupt.ttms.data.bean.studioBean


import com.google.gson.annotations.SerializedName

data class Ticket(
    @SerializedName("col")
    val col: Int,
    @SerializedName("row")
    val row: Int,
    @SerializedName("ticketId")
    val ticketId: Int,
    @SerializedName("ticketStatus")
    val ticketStatus: Int
)