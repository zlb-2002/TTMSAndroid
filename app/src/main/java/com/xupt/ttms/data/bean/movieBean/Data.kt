package com.xupt.ttms.data.bean.movieBean


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("dataSource")
    val dataSource: List<DataSource>,
    @SerializedName("sum")
    val sum: Int
)