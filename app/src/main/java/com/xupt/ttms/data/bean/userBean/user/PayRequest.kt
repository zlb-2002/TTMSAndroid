package com.xupt.ttms.data.bean.userBean.user


import com.google.gson.annotations.SerializedName

data class PayRequest(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("userId")
    val userId: Int
)