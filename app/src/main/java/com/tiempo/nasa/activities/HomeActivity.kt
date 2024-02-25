package com.tiempo.nasa.activities

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiempo.nasa.adapters.HomeAdapter
import com.tiempo.nasa.databinding.ActivityHomeBinding
import com.tiempo.nasa.domain.MainViewModel
import com.tiempo.nasa.domain.RetrofitService
import com.tiempo.nasa.domain.repository.MainRepository
import com.tiempo.nasa.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val context = this
    val adapter = HomeAdapter(context,viewModel)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, HomeViewModel(MainRepository(retrofitService)))[MainViewModel::class.java]
        binding.itemsApollo .adapter = adapter

        binding.searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }
        })

        viewModel.apolloList.observe(this, Observer {
            Log.d("HomeActivity", "onCreate: $it")
            adapter.cleanList()
            if (it != null) {
                adapter.setApolloList(it)
            }

        })
        viewModel.responseMutableLiveData.observe(this, Observer {
            Log.d("HomeActivity", "onCreate: $it")
            adapter.setApolloList(it.nasaResponse.items)
            viewModel.saveDataInPhone(context,it.nasaResponse.items)
        })
        viewModel.errorMessage.observe(this, Observer {
            viewModel.alertError(context,viewModel.errorMessage)
        })
        viewModel.getAllApollo(context)
    }
}