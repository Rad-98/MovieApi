package com.example.movieapi.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapi.R
import com.example.movieapi.adapters.FavoriteAdapter
import com.example.movieapi.database.entity.movie.MovieBookmark
import com.example.movieapi.databinding.FragmentFavoriteBinding
import com.example.movieapi.viewmodels.movies.BookmarkViewModel
import com.example.movieapi.viewmodels.movies.MovieViewModel


class FavoriteFragment : Fragment() {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]

        val layoutManager = GridLayoutManager(context,2)
        binding.recyFavorite.layoutManager = layoutManager
        adapter = FavoriteAdapter(requireContext())
        binding.recyFavorite.adapter = adapter




        getBookmarkList()

        adapter.setOnItemFavoriteClickListener(object :FavoriteAdapter.OnItemFavoriteClickListener{
            override fun onFavoriteListener(data: MovieBookmark) {
                val bundle = Bundle()
                bundle.putInt("id",data.movieId)
                navController.navigate(R.id.action_favoriteFragment_to_movieDetailFragment,bundle)
            }

            override fun onBookmarkIcomClicked(data: MovieBookmark) {
                bookmarkViewModel.deleteFromBookmark(data.movieId)
                getBookmarkList()
            }

        })



        return binding.root
    }

    private fun getBookmarkList() {
        bookmarkViewModel.getBookmarkMovie()
        bookmarkViewModel.isGet.observe(viewLifecycleOwner) {
            Log.i("rrrrr", it.toString())
            adapter.setAllFavorite(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


}