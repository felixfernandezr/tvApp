package com.example.tvapp.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Network(
    val country: Country?,
    val id: String?,
    val name: String?,
    val officialSite: String?
) : Parcelable