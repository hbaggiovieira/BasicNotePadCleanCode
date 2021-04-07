package com.example.basicnotepad.core.services.model

import android.os.Parcel
import android.os.Parcelable

data class Notes (
    var description: String,
    val id: Int = 0,
    var colorId: Int
)