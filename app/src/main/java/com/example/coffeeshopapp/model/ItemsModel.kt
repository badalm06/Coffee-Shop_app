package com.example.coffeeshopapp.model

import android.os.Parcel
import android.os.Parcelable

data class ItemsModel(
    val id: String = "",   // Unique ID for the item
    val title: String = "",
    val description: String = "",
    val picUrl: ArrayList<String> = ArrayList(),
    val price: Double = 0.0,
    val extra: String = "",
    val categoryId: String = "",
    var quantity: Int = 1,
    val size: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()  // Read quantity from Parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(picUrl)
        parcel.writeDouble(price)
        parcel.writeString(extra)
        parcel.writeString(categoryId)
        parcel.writeInt(quantity)  // Write quantity to Parcel
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<out ItemsModel?>? {
            return arrayOfNulls(size)
        }
    }
}
