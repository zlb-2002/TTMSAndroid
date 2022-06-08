package com.xupt.ttms.data.bean.order


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Ticket(
    @SerializedName("col")
    val col: Int,
    @SerializedName("row")
    val row: Int,
    @SerializedName("ticketId")
    val ticketId: Int,
    @SerializedName("ticketStatus")
    val ticketStatus: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(col)
        parcel.writeInt(row)
        parcel.writeInt(ticketId)
        parcel.writeInt(ticketStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }
}