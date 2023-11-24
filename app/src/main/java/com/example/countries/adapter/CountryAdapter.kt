package com.example.countries.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.ItemCountryBinding
import com.example.countries.model.Country
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeholderProgressBar
import com.example.countries.view.FeedFragmentDirections

/*
class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(private var itemBinding: ItemCountryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    /*
    class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

      inner class CountryHolder (private val itemCountryBinding: ItemCountryBinding): RecyclerView.ViewHolder(itemCountryBinding.root){
          fun bind(country: Country){
              itemCountryBinding.name.text= countryList[position].countryName
              itemCountryBinding.region.text= countryList[position].countryRegion
          }
      }

    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val itemCountryBinding = ItemCountryBinding.inflate(inflater,parent,false)
        //val view = inflater.inflate(R.layout.item_country, parent, false)
        //return CountryViewHolder(view)
        val itemBinding =ItemCountryBinding.inflate(inflater,parent,false)
        return CountryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    /*
      override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
          //holder.view.name.text = countryList[position].countryName
          val country: Country = countryList[position]
          holder.bind(country)
      }
     */
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        /*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }
         */

    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged() // adapter'ü yenilemek için kullanılır.
    }
}
 */
class CountryAdapter(val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(val itemBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        // Burada itemBinding içindeki öğeleri kullanabilirsiniz

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCountryBinding.inflate(inflater, parent, false)
        return CountryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country: Country = countryList[position]
        holder.itemBinding.name.text = country.countryName
        holder.itemBinding.region.text = country.countryRegion

// Arguments kullanarak yapılan bu // HATA , arguments ÇALIŞMIYOR NEDENİNİ SOR !!!!!!!
        /*
        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }
         */

// Bundle kullanarak yapılan bu
// /*
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("countryUUID",country.uuid)
            Navigation.findNavController(it).navigate(R.id.action_feedFragment_to_countryFragment,bundle)
        }

// */

        //holder.itemBinding.flagImage.downloadFromUrl(countryList[position].imageUrl!!,
        holder.itemBinding.flagImage.downloadFromUrl(countryList[position].imageUrl.orEmpty(), //countryList[position].imageUrl ifadesinin tipinin String? (nullable String) olmasından kaynaklanıyor gibi görünüyor. downloadFromUrl fonksiyonu muhtemelen bir String tipi bekliyor, ancak bu ifade nullable bir String içeriyor olabilir.
            placeholderProgressBar(holder.itemView.context)
        )

    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}
