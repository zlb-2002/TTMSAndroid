package com.xupt.ttms.data.bean.order


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Data(
    @SerializedName("endTime")
    val endTime: Long,
    @SerializedName("movieName")
    val movieName: String?,
    @SerializedName("orderExistTime")
    val orderExistTime: Long,
    @SerializedName("orderId")
    val orderId: Long,
    @SerializedName("orderStatus")
    val orderStatus: Int,
    @SerializedName("payTime")
    val payTime: Long,
    @SerializedName("price")
    val price: Double,
    @SerializedName("startTime")
    val startTime: Long,
    @SerializedName("studioName")
    val studioName: String?,
    @SerializedName("tickets")
    val tickets: ArrayList<Ticket>?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readString(),
        parcel.createTypedArrayList(Ticket)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(endTime)
        parcel.writeString(movieName)
        parcel.writeLong(orderExistTime)
        parcel.writeLong(orderId)
        parcel.writeInt(orderStatus)
        parcel.writeLong(payTime)
        parcel.writeDouble(price)
        parcel.writeLong(startTime)
        parcel.writeString(studioName)
        parcel.writeTypedList(tickets)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}