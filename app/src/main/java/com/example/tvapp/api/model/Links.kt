package com.example.tvapp.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Links(
    val previousepisode: Previousepisode?,
    val self: Self?
) : Parcelable