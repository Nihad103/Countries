package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.ItemViewBinding
import com.example.countries.model.Country
import com.example.countries.util.downloadImageFromUrl
import com.example.countries.util.progressDrawable
import com.example.countries.view.FirstFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.RowHolder>() {

    class RowHolder(var binding : ItemViewBinding) : RecyclerView.ViewHolder(binding.root), CountryClickListener {
        override fun onCountryClicked(view: View) {
            val uuid = binding.countryUuid.text.toString().toInt()
            val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment(uuid)
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val inflater = LayoutInflater.from(parent.context)
        // val view = inflater.inflate(R.layout.item_view, parent, false)
        val viewB = DataBindingUtil.inflate<ItemViewBinding>(inflater, R.layout.item_view, parent, false)
        return RowHolder(viewB)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.binding.country = countryList[position]
        holder.binding.listener = holder

        /*

        val cname = holder.view.findViewById<TextView>(R.id.cname)
        val cregion = holder.view.findViewById<TextView>(R.id.cregion)
        val imageview = holder.view.findViewById<ImageView>(R.id.imageview)

        cname.text = countryList[position].countryName
        cregion.text = countryList[position].countryRegion
        imageview.downloadImageFromUrl(countryList[position].imageUrl, progressDrawable(holder.view.context))


        holder.view.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
         */
    }


    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

}