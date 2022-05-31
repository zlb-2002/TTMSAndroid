package com.xupt.ttms.data.bean.movieBean


import com.google.gson.annotations.SerializedName

data class DataSource(
    @SerializedName("actor")
    val actor: List<String>,
    @SerializedName("area")
    val area: List<String>,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("filmlen")
    val filmlen: Int,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("language")
    val language: List<String>,
    @SerializedName("mid")
    val mid: Int,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("releaseDate")
    val releaseDate: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: List<String>
)