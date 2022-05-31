package com.xupt.ttms.data.bean.movieBean


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)