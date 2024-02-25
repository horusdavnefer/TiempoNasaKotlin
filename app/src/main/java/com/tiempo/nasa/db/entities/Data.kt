package com.tiempo.nasa.db.entities

import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("center") var center: String,
                @SerializedName("title") var title: String,
                @SerializedName("keywords") var keywords: List<String>,
                @SerializedName("nasa_id") var nasa_id: String,
                @SerializedName("date_created") var date_created: String,
                @SerializedName("media_type") var media_type: String,
                @SerializedName("description") var description: String)