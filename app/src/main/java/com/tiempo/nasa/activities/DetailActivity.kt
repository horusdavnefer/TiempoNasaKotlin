package com.tiempo.nasa.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiempo.nasa.R
import com.tiempo.nasa.databinding.ActivityDetailBinding
import com.tiempo.nasa.databinding.ActivityHomeBinding
import com.tiempo.nasa.domain.MainViewModel
import com.tiempo.nasa.domain.RetrofitService
import com.tiempo.nasa.domain.RetrofitService.Companion.retrofitService
import com.tiempo.nasa.domain.repository.MainRepository
import com.tiempo.nasa.viewmodel.HomeViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val retrofitService = RetrofitService.getInstance()

    //lateinit var viewModel: MainViewModel
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // viewModel = ViewModelProvider(this, HomeViewModel(MainRepository(retrofitService)))[MainViewModel::class.java]
        //viewModel.apolloSelected.observe(this, Observer {
        //    binding.titleNasa.text = it.data[0].title

        //})
    }
}