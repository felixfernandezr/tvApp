package com.example.tvapp.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Externals(
    val imdb: String?,
    val thetvdb: String?,
    val tvrage: String?
) : Parcelable