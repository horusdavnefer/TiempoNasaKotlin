package com.tiempo.nasa.domain

import com.tiempo.nasa.domain.response.CollectionResponse
import com.tiempo.nasa.domain.response.NasaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("/search?q=apollo%2011")
    fun getAllApollo() :retrofit2.Call<CollectionResponse>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://images-api.nasa.gov")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}