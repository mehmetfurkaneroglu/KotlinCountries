package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.countries.databinding.FragmentCountryBinding
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeholderProgressBar
import com.example.countries.viewmodel.CountryViewModel
import com.example.countries.viewmodel.FeedViewModel


class CountryFragment : Fragment() {
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var binding: FragmentCountryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_country, container, false
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var countryUuid = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
                binding.button2.setOnClickListener {
                    val action =CountryFragmentDirections.actionCountryFragmentToFeedFragment()
                    Navigation.findNavController(it).navigate(action)
                    //Navigation.findNavController(it).navigate(R.id.action_countryFragment_to_feedFragment)
                }

         */
        /*
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
         */
        arguments?.let {
            countryUuid = it.getInt("countryUUID",0)
        }

        //viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java) //ViewModelProviders, of() kaldırıldı
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                binding.countryName.text = country.countryName
                binding.countryCurrency.text = country.countryCurrency
                binding.countryCapitalName.text = country.countryCapital
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegionName.text = country.countryRegion
// RESİMLERİ ALMAK İÇİN YAPTIĞIMIZ
/*
                context?.let {
                    binding.countryFlagImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
*/

                context?.let {
                    val imageUrl = country.imageUrl!!
                    binding.countryFlagImage.downloadFromUrl(imageUrl, placeholderProgressBar(it))
                }



// burada böyle yapılabilir, RESİM İÇİN
/*
            binding.countryFlagImage.downloadFromUrl(country.imageUrl.toString(),null)

            holder.itemBinding.flagImage.downloadFromUrl(countryList[position].imageUrl.orEmpty(), //countryList[position].imageUrl ifadesinin tipinin String? (nullable String) olmasından kaynaklanıyor gibi görünüyor. downloadFromUrl fonksiyonu muhtemelen bir String tipi bekliyor, ancak bu ifade nullable bir String içeriyor olabilir.
                 placeholderProgressBar(holder.itemView.context)
            )
*/

            }
        })
    }

}