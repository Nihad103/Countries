package com.example.countries.service

import com.example.countries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    @GET("IA19-DataSetCountries/master/countrydataset.json")
    fun getCountry(): Single<List<Country>>
}