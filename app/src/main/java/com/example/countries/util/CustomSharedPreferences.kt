package com.example.countries.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CustomSharedPreferences {
    companion object{
        private val PREFERENCES_TIME = "preferences_time"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }
        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            return CustomSharedPreferences()
        }
    }
    // Zamanı kaydeden fonksiyon
    fun saveTime(time: Long) {
        // SharedPreferences üzerinde düzenleme yapabilmek için edit() metodu kullanılır
        sharedPreferences?.edit()?.apply {
            // "time" anahtarına karşılık gelen değeri kaydediyoruz
            putLong(PREFERENCES_TIME, time)
            // Değişiklikleri uygula
            apply()
            //commit() // commit kullanarak değişiklikleri kaydet
            /*
            commit() metodu, değişiklikleri hemen dosyaya yazan bir senkron işlemi gerçekleştirir. Ancak bu metot, işlem tamamlanana kadar iş parçacığını (thread) bloke eder. Bu nedenle, genellikle apply() metodu tercih edilir. apply(), iş parçacığını bloke etmeden asenkron bir şekilde değişiklikleri uygular. Bu nedenle, performans açısından apply() tercih edilen bir yöntemdir.
             */
        }
    }
    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME, 0)
}