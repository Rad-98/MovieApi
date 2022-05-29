package com.example.movieapi.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.adapters.MovieAdapter
import com.example.movieapi.database.models.DataM
import com.example.movieapi.databinding.FragmentHomeBinding
import com.example.movieapi.viewmodels.MovieViewModel
import kotlin.properties.Delegates


class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    lateinit var pP: String
    lateinit var cP: String
    lateinit var nP: String

    var pageCount by Delegates.notNull<Long>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]



        binding.nextPage.setOnClickListener {
            pP = binding.previousPage.text.toString()
            nP = binding.nextPage.text.toString()
            cP = binding.currentPage.text.toString()
            var currentPage: Long = cP.toLong()
            var nextPage: Long = nP.toLong()
            var previousPage: Long = pP.toLong()
            pageOnClick(it, previousPage, currentPage, nextPage)
        }
        binding.previousPage.setOnClickListener {
            pP = binding.previousPage.text.toString()
            nP = binding.nextPage.text.toString()
            cP = binding.currentPage.text.toString()
            var currentPage: Long = cP.toLong()
            var nextPage: Long = nP.toLong()
            var previousPage: Long = pP.toLong()
            pageOnClick(it, previousPage, currentPage, nextPage)
        }
        binding.firstPage.setOnClickListener {
            setMovieAdapterToList("1")
            binding.previousPage.text = "0"
            binding.currentPage.text = "1"
            binding.nextPage.text = "2"
            if (binding.previousPage.text == "0") {
                binding.previousPage.visibility = View.GONE
                binding.nextPage.visibility = View.VISIBLE


            }
        }
        binding.lastPage.setOnClickListener {
            setMovieAdapterToList("25")
            binding.previousPage.text = (pageCount - 1).toString()
            binding.currentPage.text = pageCount.toString()
            binding.nextPage.text = (pageCount + 1).toString()
            if (binding.nextPage.text.toString().toLong() > pageCount) {
                binding.nextPage.visibility = View.GONE
                binding.previousPage.visibility = View.VISIBLE
            }

        }



        setMovieAdapterToList(binding.currentPage.text.toString())




        if (binding.currentPage.text == "1") {
            binding.previousPage.visibility = View.GONE
        }

        return binding.root

    }

    private fun setMovieAdapterToList(cp: String) {
        val layoutManager = LinearLayoutManager(context)
        binding.mLRecycler.layoutManager = layoutManager
        adapter = context?.let { MovieAdapter(it) }!!
        binding.mLRecycler.adapter = adapter
        movieViewModel.getMovies(cp)
        movieViewModel.addMovieListResult.observe(viewLifecycleOwner) {
            it.body()?.data.let { response ->
                Log.i("fff", it.body()?.data.toString())
                response?.let { it1 ->
                    adapter.setAllMovie(it1)
                    pageCount = it.body()?.metadata?.page_count!!
                }
            }
        }
        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(dataM: DataM) {

                val fragmentManager : FragmentManager = activity!!.supportFragmentManager
                val fragment = MovieDetailFragment()
                val bundle = Bundle()
                bundle.putInt("id",dataM.id)
                fragment.arguments = bundle
                fragmentManager.beginTransaction()
                    .replace(com.example.movieapi.R.id.fragment_container,fragment,"GetId")
                    .addToBackStack(null)
                    .commit()
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun pageOnClick(v: View, preP: Long, currP: Long, nextP: Long) {
        when (v) {
            binding.nextPage -> {
                var p = preP
                var c = currP
                var n = nextP
                binding.previousPage.text = (p + 1).toString()
                binding.currentPage.text = (c + 1).toString()
                binding.nextPage.text = (n + 1).toString()
                binding.previousPage.visibility = View.VISIBLE
                setMovieAdapterToList(binding.currentPage.text.toString())

                if (binding.currentPage.text == pageCount.toString()) {
                    binding.nextPage.visibility = View.GONE
                }


            }

            binding.previousPage -> {
                var p = preP
                var c = currP
                var n = nextP
                binding.previousPage.text = (p - 1).toString()
                binding.currentPage.text = (c - 1).toString()
                binding.nextPage.text = (n - 1).toString()
                setMovieAdapterToList(binding.currentPage.text.toString())

                if (binding.nextPage.visibility == View.GONE) {
                    binding.nextPage.visibility = View.VISIBLE
                }


                if (binding.currentPage.text == "1") {
                    binding.previousPage.visibility = View.GONE
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requireActivity().supportFragmentManager.putFragment(outState, "homeFragment", HomeFragment())

    }

}