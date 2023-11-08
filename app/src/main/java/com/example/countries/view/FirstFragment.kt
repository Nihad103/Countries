package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.R
import com.example.countries.adapter.CountryAdapter
import com.example.countries.viewmodel.FirstViewModel

class FirstFragment : Fragment() {

    lateinit var viewModel: FirstViewModel
    val adapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FirstViewModel::class.java)
        viewModel.refreshData()

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter


        val swiperefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swiperefresh.setOnRefreshListener {
            recyclerview.visibility = View.GONE
            swiperefresh.isRefreshing = false
            val progressBar = view.findViewById<ProgressBar>(R.id.progrressBar)
            progressBar.visibility = View.VISIBLE
            observeLiveData()
            viewModel.getDataFromApi()
        }

        observeLiveData()


//        val firstbtn = view.findViewById<Button>(R.id.firstbtn)
//        firstbtn.setOnClickListener{
//            val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    fun observeLiveData() {

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            val recyclerview = view?.findViewById<RecyclerView>(R.id.recyclerView)
            countries?.let {
                recyclerview?.visibility = View.VISIBLE
                adapter.updateCountryList(countries)
            }
        })

        viewModel.errorText.observe(viewLifecycleOwner, Observer { error ->
            val errorText = view?.findViewById<TextView>(R.id.errorText)
            val recyclerview = view?.findViewById<RecyclerView>(R.id.recyclerView)
            error?.let {
                if (it) {
                    errorText?.visibility = View.VISIBLE
                    recyclerview?.visibility = View.GONE
                } else {
                    errorText?.visibility = View.GONE

                }
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {progress ->
            val progressBar = view?.findViewById<ProgressBar>(R.id.progrressBar)
            val recyclerview = view?.findViewById<RecyclerView>(R.id.recyclerView)
            val errorText = view?.findViewById<TextView>(R.id.errorText)
            progress?.let {
                if (it) {
                    progressBar?.visibility = View.VISIBLE
                    recyclerview?.visibility = View.GONE
                    errorText?.visibility = View.GONE
                } else {
                    progressBar?.visibility = View.GONE
                }
            }
        })
    }

}