package com.tiempo.nasa.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Apollo(@SerializedName("href") var href:String,
                  @SerializedName("data") var data:  ArrayList<Data>,
                  @SerializedName("links") var links: ArrayList<Links>)  {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var thisid = 0

    @ColumnInfo(name = "href")
    var thishref = href

    @ColumnInfo(name = "isFavorite")
    var thisisFavorite = false

    @ColumnInfo(name = "data")

    var thisdata = data

    @ColumnInfo(name = "links")
    var thislinks = links


}