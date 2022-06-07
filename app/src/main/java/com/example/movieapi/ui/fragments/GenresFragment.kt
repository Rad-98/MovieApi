package com.example.movieapi.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.R
import com.example.movieapi.adapters.GenresAdapter
import com.example.movieapi.databinding.FragmentGenresBinding
import com.example.movieapi.viewmodels.movies.MovieViewModel
import kotlin.properties.Delegates


class GenresFragment : Fragment() {

    private lateinit var binding:FragmentGenresBinding
    private lateinit var viewModel:MovieViewModel
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var navController:NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenresBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]




        val layoutManager = GridLayoutManager(context,3)
        binding.recyGenres.layoutManager = layoutManager
        genresAdapter = GenresAdapter(requireContext())
        binding.recyGenres.adapter = genresAdapter
        viewModel.getGenres()
        viewModel.genresResult.observe(viewLifecycleOwner) {
            genresAdapter.setAllGenres(it)
            Log.i("Gen", it.toString())
        }
        genresAdapter.setOnItemClickListener(object :GenresAdapter.OnItemClicklistener{
            override fun setOnItemClick(genresId: Int) {
                Toast.makeText(context,"id : $genresId",Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("SORT",genresId.toString())
                navController.navigate(R.id.action_genresFragment_to_showListFragment,bundle)
            }
        })

        return binding.root
    }




}