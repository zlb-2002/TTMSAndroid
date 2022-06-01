package com.xupt.ttms.data.bean.userBean.user


import com.google.gson.annotations.SerializedName

data class UpdateReponse(
    @SerializedName("data")
    val `data`: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)