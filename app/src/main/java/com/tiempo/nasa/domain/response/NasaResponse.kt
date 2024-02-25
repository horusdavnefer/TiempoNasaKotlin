package com.tiempo.nasa.domain.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tiempo.nasa.db.entities.Apollo

data class NasaResponse(
    @SerializedName("items")
    @Expose
    val items: ArrayList<Apollo>,
    @SerializedName("version")
    @Expose
    val version: String,
    @SerializedName("href")
    @Expose
    val href: String
)