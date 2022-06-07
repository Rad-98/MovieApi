package com.example.movieapi.ui.fragments

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.R
import com.example.movieapi.adapters.MovieAdapter
import com.example.movieapi.adapters.MovieAdapterMain
import com.example.movieapi.api.models.movies.DataM
import com.example.movieapi.database.entity.movie.MovieBookmark
import com.example.movieapi.databinding.FragmentShowListBinding
import com.example.movieapi.viewmodels.movies.BookmarkViewModel
import com.example.movieapi.viewmodels.movies.MovieViewModel
import kotlin.properties.Delegates

class ShowListFragment : Fragment() {

    private lateinit var binding: FragmentShowListBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var navController: NavController

    var pageCount by Delegates.notNull<Long>()
    lateinit var pP: String
    lateinit var cP: String
    lateinit var nP: String

    private var SORT: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SORT = requireArguments().getString("SORT").toString()




        binding = FragmentShowListBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]

        binding.previousPage.text = movieViewModel.previousPage.toString()
        binding.currentPage.text = movieViewModel.currentPage.toString()
        binding.nextPage.text = movieViewModel.nextPage.toString()

        binding.nextPage.setOnClickListener {
            pP = binding.previousPage.text.toString()
            nP = binding.nextPage.text.toString()
            cP = movieViewModel.currentPage.toString()
            pageOnClick(it)
        }
        binding.previousPage.setOnClickListener {
            pP = binding.previousPage.text.toString()
            nP = binding.nextPage.text.toString()
            cP = movieViewModel.currentPage.toString()
            pageOnClick(it)
        }
        binding.firstPage.setOnClickListener {
            movieViewModel.currentPage = 1
            movieViewModel.nextPage = 2
            movieViewModel.previousPage = 0
            setAllMovieToList(SORT)
            binding.previousPage.text = "0"
            binding.currentPage.text = "1"
            binding.nextPage.text = "2"
            if (binding.previousPage.text == "0") {
                binding.previousPage.visibility = View.GONE
                binding.nextPage.visibility = View.VISIBLE
            }
        }
        binding.lastPage.setOnClickListener {
            movieViewModel.currentPage = 25
            movieViewModel.nextPage = 26
            movieViewModel.previousPage = 24
            setAllMovieToList(SORT)
            binding.previousPage.text = movieViewModel.previousPage.toString()
            binding.currentPage.text = pageCount.toString()
            binding.nextPage.text = movieViewModel.nextPage.toString()
            if (binding.nextPage.text.toString().toLong() > pageCount) {
                binding.nextPage.visibility = View.GONE
                binding.previousPage.visibility = View.VISIBLE
            }

        }

        if (binding.currentPage.text == "1") {
            binding.previousPage.visibility = View.GONE
        }

        setAllMovieToList(SORT)
        return binding.root
    }

    private fun setAllMovieToList(sort: String) {
        val layoutManager = LinearLayoutManager(context)
        binding.recyAllTag.layoutManager = layoutManager
        adapter = MovieAdapter(context)
        binding.recyAllTag.adapter = adapter

        if (sort == "NEW" || sort == "IMDB" || sort == "ID") {
            movieViewModel.getMovies(movieViewModel.currentPage.toString())
            movieViewModel.addMovieListResult.observe(viewLifecycleOwner) {
                it.body()?.data.let { response ->
                    response?.let { it1 ->
                        adapter.setAllMovie(it1, sort)
                        pageCount = it.body()?.metadata?.page_count!!
                    }
                }
            }
        } else {
            movieViewModel.getMoviesByGenre(SORT.toInt(),movieViewModel.currentPage)
            movieViewModel.addMovieFilterListResult.observe(viewLifecycleOwner) { response ->
                response.body()?.data.let {
                    adapter.setAllMovie(it!!, "ID")
                    pageCount = response.body()?.metadata?.page_count!!
                }


            }
        }



        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(dataM: DataM) {
                val bundle = Bundle()
                bundle.putInt("id", dataM.id)
                navController.navigate(R.id.action_showListFragment_to_movieDetailFragment, bundle)
            }
        })

        adapter.setOnBookmarkClickListener(object : MovieAdapter.OnBookmarkClickListener {
            override fun onBookmarkClick(data: DataM) {
                try {
                    bookmarkViewModel.getBookmarkById(data.id)
                    bookmarkViewModel.isBook?.observe(viewLifecycleOwner) {
                        if (it != null) {
                            Log.i("BM", it.movieId.toString())
                            bookmarkViewModel.deleteFromBookmark(data.id)
                            bookmarkViewModel.isDelete.observe(viewLifecycleOwner) { response ->
                                Log.i("DDDDM", "delete :" + response.toString())
                                if (response == true) {
                                    Toast.makeText(
                                        context,
                                        "Movie deleted from favorite.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            bookmarkViewModel.addBookmark(
                                MovieBookmark(
                                    movieId = data.id,
                                    poster = data.poster,
                                    imdb_rating = data.imdb_rating,
                                    title = data.title
                                )
                            )
                            bookmarkViewModel.isAdd!!.observe(viewLifecycleOwner) {
                                if (it == true) {
                                    Toast.makeText(
                                        context,
                                        "Movie added to favorite.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                }

                            }

                        }

                    }
                } catch (e: Exception) {

                }

            }


        }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun pageOnClick(v: View) {
        when (v) {
            binding.nextPage -> {

                binding.previousPage.text = (movieViewModel.previousPage + 1).toString()
                binding.currentPage.text = (movieViewModel.currentPage + 1).toString()
                binding.nextPage.text = (movieViewModel.nextPage + 1).toString()
                binding.previousPage.visibility = View.VISIBLE
                movieViewModel.previousPage = binding.previousPage.text.toString().toInt()
                movieViewModel.currentPage = binding.currentPage.text.toString().toInt()
                movieViewModel.nextPage = binding.nextPage.text.toString().toInt()
                setAllMovieToList(SORT)

                if (binding.currentPage.text == pageCount.toString()) {
                    binding.nextPage.visibility = View.GONE
                }


            }

            binding.previousPage -> {

                binding.previousPage.text = (movieViewModel.previousPage - 1).toString()
                binding.currentPage.text = (movieViewModel.currentPage - 1).toString()
                binding.nextPage.text = (movieViewModel.nextPage - 1).toString()
                movieViewModel.previousPage = binding.previousPage.text.toString().toInt()
                movieViewModel.currentPage = binding.currentPage.text.toString().toInt()
                movieViewModel.nextPage = binding.nextPage.text.toString().toInt()
                setAllMovieToList(SORT)

                if (binding.nextPage.visibility == View.GONE) {
                    binding.nextPage.visibility = View.VISIBLE
                }


                if (binding.currentPage.text == "1") {
                    binding.previousPage.visibility = View.GONE
                }
            }
        }
    }

}