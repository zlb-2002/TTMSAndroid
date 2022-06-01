package com.xupt.ttms.data.bean.userBean.user


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("age")
    var age: Int,
    @SerializedName("balance")
    val balance: Int,
    @SerializedName("email")
    var email: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("introduce")
    var introduce: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("portrait")
    val portrait: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    var username: String
)