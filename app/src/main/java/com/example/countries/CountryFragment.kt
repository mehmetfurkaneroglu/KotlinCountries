package com.example.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.countries.databinding.FragmentCountryBinding


class CountryFragment : Fragment() {
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

        binding.button2.setOnClickListener {
            val action = CountryFragmentDirections.actionCountryFragmentToFeedFragment()
            Navigation.findNavController(it).navigate(action)
            //Navigation.findNavController(it).navigate(R.id.action_countryFragment_to_feedFragment)
        }
 /*
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

  */
    }

}