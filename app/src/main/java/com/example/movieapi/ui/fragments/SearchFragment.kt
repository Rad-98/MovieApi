package com.example.movieapi.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.R
import com.example.movieapi.adapters.MovieAdapter
import com.example.movieapi.api.models.movies.DataM
import com.example.movieapi.database.entity.movie.MovieBookmark
import com.example.movieapi.databinding.FragmentSearchBinding
import com.example.movieapi.viewmodels.movies.BookmarkViewModel
import com.example.movieapi.viewmodels.movies.MovieViewModel
import java.lang.Exception


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MovieViewModel
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var etSearchTitle: EditText
    private lateinit var adapter: MovieAdapter
    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        searchRecyclerView = binding.recyclerSearchByTitle
        etSearchTitle = binding.etSearchByTitle
        searchByTitle(binding.etSearchByTitle.text.toString())
        etSearchTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchByTitle(s.toString())
            }
        })

        return binding.root
    }

    private fun searchByTitle(text: String) {
        val layoutManager = LinearLayoutManager(context)
        searchRecyclerView.layoutManager = layoutManager
        adapter = context?.let { MovieAdapter(it) }!!
        viewModel.getMoviesByTitle(text)
        viewModel.addMovieFilterListResult.observe(viewLifecycleOwner) {
            it.body()?.data.let { response ->
                if (it.body()?.data?.isEmpty() == true) {
                    binding.txtNotFound.visibility = View.VISIBLE
                    binding.txtFirst.visibility = View.GONE
                    searchRecyclerView.visibility = View.GONE
                } else {
                    searchRecyclerView.visibility = View.VISIBLE
                    binding.txtFirst.visibility = View.GONE
                    binding.txtNotFound.visibility = View.GONE
                    response?.let { it1 ->
                        adapter.setAllMovie(it1, "NEW")
                        searchRecyclerView.adapter = adapter
                    }
                }
            }
        }
        adapter.setOnItemClickListener(object :MovieAdapter.OnItemClickListener{
            override fun onItemClick(dataM: DataM) {
                val bundle = Bundle()
                bundle.putInt("id",dataM.id)
                navController.navigate(R.id.action_searchFragment_to_movieDetailFragment,bundle)
            }
        })
        adapter.setOnBookmarkClickListener(object : MovieAdapter.OnBookmarkClickListener {
            override fun onBookmarkClick(data: DataM) {
                try {
                    bookmarkViewModel.getBookmarkById(data.id)
                    bookmarkViewModel.isBook?.observe(viewLifecycleOwner){
                        if (it != null){
                            Log.i("BM",it.movieId.toString())
                            bookmarkViewModel.deleteFromBookmark(data.id)
                            bookmarkViewModel.isDelete.observe(viewLifecycleOwner){ response ->
                                Log.i("DDDDM","delete :"+response.toString())
                                if (response == true){
                                    Toast.makeText(
                                        context,
                                        "Movie deleted from favorite.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }else{
                            bookmarkViewModel.addBookmark(
                                MovieBookmark(
                                    movieId = data.id,
                                    poster = data.poster,
                                    imdb_rating = data.imdb_rating,
                                    title = data.title
                                )
                            )
                            bookmarkViewModel.isAdd!!.observe(viewLifecycleOwner){
                                if (it == true){
                                    Toast.makeText(context,"Movie added to favorite.", Toast.LENGTH_SHORT).show()
                                }else{
                                }

                            }

                        }

                    }
                }catch (e: Exception){

                }

            }


        }
        )

    }

}
