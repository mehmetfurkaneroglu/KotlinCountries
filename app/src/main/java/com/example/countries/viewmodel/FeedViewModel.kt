package com.example.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.model.Country
import com.example.countries.service.CountryAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class FeedViewModel: ViewModel() {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() // Call yaptıkça bunun içine atıyoruz disposable kullan at demek


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        /*
        val country = Country("Turkiye","Asia","Ankara","TRY","Turkish","www.ss.com")
        val country2 = Country("Fransa","Europe","Paris","EUR","Turkish","www.ss.com")
        val country3 = Country("Almanya","Europe","Berlin","EUR","Turkish","www.ss.com")

        val countryList = arrayListOf<Country>(country,country2,country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
         */
        getDataFromAPI()
    }

    private fun getDataFromAPI(){
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){

                    override fun onSuccess(t: List<Country>){
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }
                    override fun onError (e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })

        )
    }

}