package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.util.downloadImageFromUrl
import com.example.countries.util.progressDrawable
import com.example.countries.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    lateinit var detailViewModel: DetailViewModel
    private var detailUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            detailUuid = DetailFragmentArgs.fromBundle(it).detailUuid
        }

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        detailViewModel.getDataFromRoom(detailUuid)

        observeLiveData()
    }


    private fun observeLiveData() {
        detailViewModel.countries.observe(viewLifecycleOwner, Observer { country ->
            val countryName = view?.findViewById<TextView>(R.id.countryName)
            val countryRegion = view?.findViewById<TextView>(R.id.countryRegion)
            val countryCapital = view?.findViewById<TextView>(R.id.countryCapital)
            val countryCurrency = view?.findViewById<TextView>(R.id.countryCurrency)
            val countryLanguage = view?.findViewById<TextView>(R.id.countryLanguage)
            val countryImage = view?.findViewById<ImageView>(R.id.countryImage)

            country?.let {
                countryName?.text = country.countryName
                countryRegion?.text = country.countryRegion
                countryCapital?.text = country.countryCapital
                countryCurrency?.text = country.countryCurrency
                countryLanguage?.text = country.countryLanguage

                /*
                Dogru olan budu
                context?.let {
                    countryImage?.downloadImageFromUrl(country.imageUrl, progressDrawable(context!!))
                }
                */

                // Bu men yazandi
                countryImage?.downloadImageFromUrl(it.imageUrl, progressDrawable(context!!))
            }
        })
    }
}