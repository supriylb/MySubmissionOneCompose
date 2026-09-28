package com.example.mysubmissiononecompose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String,
) : Parcelable
