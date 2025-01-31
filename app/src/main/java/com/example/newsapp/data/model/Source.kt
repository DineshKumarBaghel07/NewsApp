package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
   val id: Int,
    val name: String
)