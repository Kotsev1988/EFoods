package com.example.core.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dishe(
    val description: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val price: Int,
    val tegs: List<String>,
    val weight: Int,
    val count: Int
): Parcelable