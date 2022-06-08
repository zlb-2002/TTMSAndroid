package com.xupt.ttms.data.bean.movieBean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class DataSource(
    @SerializedName("actor")
    val actor: ArrayList<String>?,
    @SerializedName("area")
    val area: ArrayList<String>?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("filmlen")
    val filmlen: Int,
    @SerializedName("introduction")
    val introduction: String?,
    @SerializedName("language")
    val language: ArrayList<String>?,
    @SerializedName("mid")
    val mid: Int,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("releaseDate")
    val releaseDate: Long,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: ArrayList<String>?
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(actor)
        parcel.writeStringList(area)
        parcel.writeString(cover)
        parcel.writeInt(filmlen)
        parcel.writeString(introduction)
        parcel.writeStringList(language)
        parcel.writeInt(mid)
        parcel.writeDouble(rate)
        parcel.writeLong(releaseDate)
        parcel.writeString(title)
        parcel.writeStringList(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataSource> {
        override fun createFromParcel(parcel: Parcel): DataSource {
            return DataSource(parcel)
        }

        override fun newArray(size: Int): Array<DataSource?> {
            return arrayOfNulls(size)
        }
    }

}