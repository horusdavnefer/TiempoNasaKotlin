package com.tiempo.nasa.domain

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiempo.nasa.db.AppDatabase
import com.tiempo.nasa.db.entities.Apollo
import com.tiempo.nasa.domain.repository.MainRepository
import com.tiempo.nasa.domain.response.CollectionResponse
import com.tiempo.nasa.helpers.AppExecutors
import com.tiempo.nasa.helpers.Utils
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val repository: MainRepository):ViewModel() {

    val apolloList = MutableLiveData<List<Apollo>?>()
    val errorMessage = MutableLiveData<String>()
    val responseMutableLiveData = MutableLiveData<CollectionResponse>()
    var apolloSelected = MutableLiveData<Apollo>()

    fun setApolloSelected(apollo: Apollo) {
        apolloSelected.postValue(apollo)
    }

    fun getAllApollo(context: Context) {

        if (Utils.isConnected(context)) {
            val response = repository.getAllApollo()
            response.enqueue(object : retrofit2.Callback<CollectionResponse> {
                override fun onResponse(call: Call<CollectionResponse>, response: Response<CollectionResponse>) {
                    responseMutableLiveData.value = response.body()
                }

                override fun onFailure(call: Call<CollectionResponse>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            })
        } else {
            getDataLocal(context)
        }


    }

    fun saveDataInPhone(context: Context, apollo: List<Apollo>){
        AppExecutors.instance!!.diskIO().execute {
            for (apo in apollo) {
                //AppDatabase.getInstance(context)!!.apolloDao.addApollo(apo)
            }
        }

    }

    fun getDataLocal(context: Context) {
        AppExecutors.instance!!.diskIO().execute {
            val value = AppDatabase.getInstance(context)!!.apolloDao!!.getAllApollo()
            if (value!!.isNotEmpty()) {
                apolloList.value = value as List<Apollo>?
            } else {
                errorMessage.postValue("No data")
            }

        }
    }

    fun alertError(context: Context, msg: MutableLiveData<String>){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Lo sentimos")
        builder.setMessage(msg.value)

        val alertDialog = builder.create()

        builder.setPositiveButton("Reintentar") { _, _ ->
            getAllApollo(context)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun searchItem(nextText:String,apollo: List<Apollo>){
        var searchList = ArrayList<Apollo>()
        for (apo in apollo){
            for (data in apo.data){
                if (data.keywords.contains(nextText)) {
                    searchList.add(apo)
                }
            }
        }
        apolloList.postValue(searchList)
    }


}