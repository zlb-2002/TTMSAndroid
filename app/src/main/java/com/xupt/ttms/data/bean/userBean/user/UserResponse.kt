package com.xupt.ttms.data.bean.userBean.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: Any,
    @SerializedName("status")
    val status: Int
)