package com.example.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.model.Country
import com.example.countries.service.CountryAPIService
import com.example.countries.service.CountryDatabase
import com.example.countries.util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

//class FeedViewModel: ViewModel() {
class FeedViewModel(application: Application): BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() // Call yaptıkça bunun içine atıyoruz disposable kullan at demek
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L //bu bana nano saniyeyi veriyor. (10 dakika demek)


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
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && (System.nanoTime()-updateTime < refreshTime)) {
            getDataFromSQLite()
        }
        else {
            getDataFromAPI()
        }
    }
    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQlite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){

                    override fun onSuccess(t: List<Country>){
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API", Toast.LENGTH_LONG).show()
                    }
                    override fun onError (e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })

        )
    }

    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    //coroutine scope unda yapıyoruz launch vermemiz yeterli, ülkleri sildik sonra tek tek ekledik.
    private fun storeInSQLite(list: List<Country>) { //BaseViewModel'ı yaptığım için bundn sonrası kolay, onu hallet gerisi  basit
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // diziden , tek tek hale getiriyor ->list ->individual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}