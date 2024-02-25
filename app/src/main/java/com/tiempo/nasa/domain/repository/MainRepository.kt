package com.tiempo.nasa.domain.repository

import com.tiempo.nasa.domain.RetrofitService

class MainRepository(private val retrofitService: RetrofitService) {
    fun getAllApollo() = retrofitService.getAllApollo()
}