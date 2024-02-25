package com.tiempo.nasa.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tiempo.nasa.R
import com.tiempo.nasa.activities.DetailActivity
import com.tiempo.nasa.databinding.ItemNasaBinding
import com.tiempo.nasa.db.AppDatabase
import com.tiempo.nasa.db.entities.Apollo
import com.tiempo.nasa.domain.MainViewModel
import com.tiempo.nasa.helpers.AppExecutors
import java.util.Locale

class HomeAdapter(var mContext: Context, val viewModel: MainViewModel): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(),
    Filterable {

    var nasaList = mutableListOf<Apollo>()
    var filterNatalist = mutableListOf<Apollo>()
    inner class HomeViewHolder( var view: ItemNasaBinding )
        : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemNasaBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filterNatalist.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setApolloList(nasaList: List<Apollo>) {

        this.nasaList = nasaList.toMutableList()
        this.filterNatalist = nasaList.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun cleanList(){
        this.nasaList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val view = holder.view
        val apollo = filterNatalist[position]
        view.titleNasa.text = apollo.data[0].title
        Picasso.get().load(apollo.links[0].href).into(view.imageNasa)
        if (apollo.thisisFavorite){
            view.favorite.setImageResource(R.mipmap.ic_favorite)
        } else {
            view.favorite.setImageResource(R.mipmap.ic_no_favorite)
        }
        view.favorite.setOnClickListener {
            apollo.thisisFavorite = !apollo.thisisFavorite
            AppExecutors.instance!!.diskIO().execute {
                AppDatabase.getInstance(mContext)!!.apolloDao!!.updateApollo(apollo)
            }
        }

        view.main.setOnClickListener {
            viewModel.setApolloSelected(apollo)
            val i = Intent(mContext, DetailActivity::class.java)
            mContext.startActivity(i)
        }


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSequenceString = constraint.toString()
                if (charSequenceString.isEmpty()) {
                    filterNatalist = nasaList
                } else {
                    val filteredList: MutableList<Apollo> = ArrayList<Apollo>()
                    for (name in nasaList) {
                        for (data in name.data){
                            for (text in data.keywords){
                                if (text.lowercase(Locale.ROOT)
                                        .contains(charSequenceString.lowercase(Locale.getDefault()))
                                ) {
                                    filteredList.add(name)
                                }
                            }
                        }

                        filterNatalist = filteredList
                    }

                }
                val results = FilterResults()
                results.values = filterNatalist
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filterNatalist = (results.values as List<Apollo>).toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}