package com.example.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.countries.model.Country
import com.example.countries.service.CountryDatabase
import com.example.countries.service.RetrofitInstance
import com.example.countries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FirstViewModel(application: Application): BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val retrofitInstance = RetrofitInstance()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 0.1 * 60 * 1000 * 1000 * 1000L     // Bu 10 deqiqedi ( Long ile )

//    bunu mutleq yoxla -> lateinit bunuda yoxla
//    private var customSharedPreferences: CustomSharedPreferences? = null


    val countries = MutableLiveData<List<Country>>()
    val errorText = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()


    fun refreshData() {
        var updateTime = customSharedPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromApi()
        }
    }



    fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "get SQLite", Toast.LENGTH_SHORT).show()
        }
    }



    fun getDataFromApi() {
        progressBar.value = true
        disposable.add(
            retrofitInstance.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "get API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        progressBar.value = false
                        errorText.value = true
                        e.printStackTrace()
                    }

                })
        )
    }


    private fun showCountries(countryList: List<Country>) {
        progressBar.value = false
        errorText.value = false
        countries.value = countryList
    }

    private fun storeInSQLite(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }
}