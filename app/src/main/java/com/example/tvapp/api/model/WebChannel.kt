package com.example.tvapp.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WebChannel(
    val country: String?,
    val id: String?,
    val name: String?,
    val officialSite: String?
) : Parcelable