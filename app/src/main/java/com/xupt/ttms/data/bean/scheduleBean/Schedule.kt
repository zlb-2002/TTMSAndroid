package com.xupt.ttms.data.bean.scheduleBean


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("endTime")
    val endTime: Long,
    @SerializedName("movieId")
    val movieId: Int,
    @SerializedName("movieName")
    val movieName: String?,
    @SerializedName("scheduleId")
    val scheduleId: Int,
    @SerializedName("startTime")
    val startTime: Long,
    @SerializedName("status")
    val status: Int,
    @SerializedName("studioId")
    val studioId: Int,
    @SerializedName("studioName")
    val studioName: String?,
    @SerializedName("ticketPrice")
    val ticketPrice: Int
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(endTime)
        parcel.writeInt(movieId)
        parcel.writeString(movieName)
        parcel.writeInt(scheduleId)
        parcel.writeLong(startTime)
        parcel.writeInt(status)
        parcel.writeInt(studioId)
        parcel.writeString(studioName)
        parcel.writeInt(ticketPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Schedule> {
        override fun createFromParcel(parcel: Parcel): Schedule {
            return Schedule(parcel)
        }

        override fun newArray(size: Int): Array<Schedule?> {
            return arrayOfNulls(size)
        }
    }
}