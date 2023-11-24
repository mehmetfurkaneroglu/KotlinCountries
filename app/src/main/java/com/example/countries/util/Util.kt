package com.example.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.countries.R
import java.lang.reflect.Parameter

// Extension

// GLide'ı tekrar tekrar tanımlamadan buradan çağıracağım.
fun ImageView.downloadFromUrl(url: String,progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable) // placeholder : resim inene kadar ne göstereceğim onu gösteriyor
        .error(R.drawable.ic_launcher_background) //ic_launcher_round

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
//textler çok hızlı inerken imagelar o kadar hızlı inmiyor
fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f //ne kadar büyüklükte genişlikte olacağı
        centerRadius = 40f //yarıçapı
        start()
    }
}