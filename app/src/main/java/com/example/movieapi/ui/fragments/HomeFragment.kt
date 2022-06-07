package com.example.movieapi.ui.fragments

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.R
import com.example.movieapi.adapters.GenresAdapter
import com.example.movieapi.adapters.ImageSlideAdapter
import com.example.movieapi.adapters.MovieAdapterMain
import com.example.movieapi.api.models.movies.DataM
import com.example.movieapi.databinding.FragmentHomeBinding
import com.example.movieapi.viewmodels.movies.BookmarkViewModel
import com.example.movieapi.viewmodels.movies.MovieViewModel
import me.relex.circleindicator.CircleIndicator
import kotlin.properties.Delegates


class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var adapter: MovieAdapterMain
    private lateinit var adapterNew: MovieAdapterMain
    private lateinit var adapterTopRate: MovieAdapterMain
    private lateinit var navController: NavController
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

    var sliderImages :ArrayList<String> = arrayListOf(
        "https://benarasmediaworks.com/data1/images/article15movieposter.jpg",
        "https://cdn.vox-cdn.com/thumbor/CE2yb6TcB1B0XYz2dTsy4lV8NNQ=/45x0:1395x1013/1200x800/filters:focal(45x0:1395x1013)/cdn.vox-cdn.com/uploads/chorus_image/image/35440606/inception.0.jpg",
        "https://is1-ssl.mzstatic.com/image/thumb/Video124/v4/f8/0c/7b/f80c7b1c-f820-a8f3-9825-cdea13d013ef/pr_source.lsr/1200x675.jpg",
        "https://i.pinimg.com/736x/cc/8e/ab/cc8eab463420d48b51f271bf886a14a8.jpg",
        "https://www.popoptiq.com/wp-content/uploads/2012/11/prometheus-poster1.jpg",
        "https://thayermemoriallibrary.org/wp-content/uploads/2019/05/Cold-Pursuit-Movie-Website-SliderTemplate2.pub-jpeg.jpg"
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]


        binding.listOfGenres.setOnClickListener {
            Toast.makeText(context,"dfsf0",Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_homeFragment_to_genresFragment)
        }

        setTopRateToRecyclers()
        setAllMovieToRecyclers()
        setNewToRecyclers()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)



        viewPagerAdapter = ImageSlideAdapter(requireContext(), sliderImages)
        binding.slider.adapter = viewPagerAdapter

        indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(binding.slider)


        binding.iconGoToAllMovie.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("SORT","ID")
            navController.navigate(R.id.action_homeFragment_to_showListFragment,bundle)
        }

        binding.iconGoToNew.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("SORT","NEW")
            navController.navigate(R.id.action_homeFragment_to_showListFragment,bundle)
        }

        binding.iconGoToTop.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("SORT","IMDB")
            navController.navigate(R.id.action_homeFragment_to_showListFragment,bundle)
        }
    }

    private fun setAllMovieToRecyclers() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.allRecycler.layoutManager = layoutManager
        adapter = context?.let { MovieAdapterMain(it) }!!
        binding.allRecycler.adapter = adapter
        movieViewModel.getMovies(movieViewModel.currentPage.toString())
        movieViewModel.addMovieListResult.observe(viewLifecycleOwner) {
            it.body()?.data.let { response ->
                response?.let { it1 ->
                    adapter.setAllMovie(it1, "ID")
                }
            }
        }

        adapter.setOnItemClickListener(object : MovieAdapterMain.OnItemClickListener {
            override fun onItemClick(dataM: DataM) {
                val bundle = Bundle()
                bundle.putInt("id", dataM.id)
                navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
            }
        })


    }

    private fun setNewToRecyclers() {
        val layoutManagerNew = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.newRecycler.layoutManager = layoutManagerNew
        adapterNew = context?.let { MovieAdapterMain(it) }!!
        binding.newRecycler.adapter = adapterNew
        movieViewModel.getMovies(movieViewModel.currentPage.toString())
        movieViewModel.addMovieListResult.observe(viewLifecycleOwner) {
            it.body()?.data.let { response ->
                response?.let { it1 ->
                    adapterNew.setAllMovie(it1, "NEW")
                }
            }
        }

        adapterNew.setOnItemClickListener(object : MovieAdapterMain.OnItemClickListener {
            override fun onItemClick(dataM: DataM) {
                val bundle = Bundle()
                bundle.putInt("id", dataM.id)
                navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
            }

        })
    }

    private fun setTopRateToRecyclers() {

        val layoutManagerTopRate =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.topRecycler.layoutManager = layoutManagerTopRate
        adapterTopRate = context?.let { MovieAdapterMain(it) }!!
        binding.topRecycler.adapter = adapterTopRate
        movieViewModel.getMovies(movieViewModel.currentPage.toString())
        movieViewModel.addMovieListResult.observe(viewLifecycleOwner) {
            it.body()?.data.let { response ->
                response?.let { it1 ->
                    adapterTopRate.setAllMovie(it1, "IMDB")
                }
            }
        }

        adapterTopRate.setOnItemClickListener(object : MovieAdapterMain.OnItemClickListener {
            override fun onItemClick(dataM: DataM) {
                val bundle = Bundle()
                bundle.putInt("id", dataM.id)
                navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
            }

        })

    }



}