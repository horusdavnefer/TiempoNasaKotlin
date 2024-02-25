package com.tiempo.nasa.domain.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CollectionResponse(@SerializedName("collection")
                              @Expose var nasaResponse: NasaResponse)