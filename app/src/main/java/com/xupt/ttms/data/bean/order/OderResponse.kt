package com.xupt.ttms.data.bean.order


import com.google.gson.annotations.SerializedName

data class OderResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)