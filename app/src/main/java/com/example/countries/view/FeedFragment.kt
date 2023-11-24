package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.adapter.CountryAdapter
import com.example.countries.databinding.FragmentFeedBinding
import com.example.countries.viewmodel.FeedViewModel

/*
class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_feed, container, false)
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        binding.button.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
            //Navigation.findNavController(it).navigate(R.id.action_feedFragment_to_countryFragment)
        }

 */
        val viewModel = ViewModelProvider(this).get(FeedViewModel::class.java) //ViewModelProviders kaldırıldı
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context) //yan yana oluşturmak isteseydik gridLinearLayout kullanacaktık
        binding.countryList.adapter = countryAdapter

        // HATA //observeLiveData() // observeLiveData fonksiyonunu çağırarak verilere gözlemci ekleyin

    }

    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries -> //this yerine viewLifecycleOwner kullanıyoruz
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let{
                if(it) {
                    binding.countryError.visibility = View.VISIBLE
                }
                else {
                    binding.countryError.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE //loading yükleniyorken bunları gizlememiz lazım
                    binding.countryError.visibility = View.GONE //loading yükleniyorken bunları gizlememiz lazım
                }
                else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }
}

 */
class FeedFragment : Fragment() {

    private val countryAdapter = CountryAdapter(arrayListOf())
    private lateinit var viewModel: FeedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_feed, container, false)
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
                binding.button.setOnClickListener {
                    val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
                    Navigation.findNavController(it).navigate(action)
                    //Navigation.findNavController(it).navigate(R.id.action_feedFragment_to_countryFragment)
                }

         */
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java) //ViewModelProviders kaldırıldı
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context) //yan yana oluşturmak isteseydik gridLinearLayout kullanacaktık
        binding.countryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            //binding.countryLoading.visibility = View.VISIBLE
            //viewModel.refreshData() // sayfayı yenileyince apı dan indirsin diye bu işlemi yaptık
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData() // observeLiveData fonksiyonunu çağırarak verilere gözlemci ekleyin

    }

    fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let{
                if(it) {
                    binding.countryError.visibility = View.VISIBLE
                } else {
                    binding.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                } else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }
}