package com.xupt.ttms.data.bean.userBean.login


import com.google.gson.annotations.SerializedName

data class CodeResponse(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)