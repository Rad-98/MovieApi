package com.example.movieapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapi.R
import com.example.movieapi.adapters.ImageSlideAdapter
import com.example.movieapi.databinding.FragmentHomeBinding
import com.example.movieapi.databinding.FragmentMovieDetailBinding
import com.example.movieapi.viewmodels.MovieViewModel
import me.relex.circleindicator.CircleIndicator


class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var movieViewModel: MovieViewModel

    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        val bundle = this.arguments
        var movieId = bundle?.get("id")
        val id = movieId.toString().toLong()

        movieViewModel.getMovieById(id)
        movieViewModel.addMovieDetailResult.observe(viewLifecycleOwner){
            binding.txtTitle.text = it.title
            Glide
                .with(this)
                .load(it.poster)
                .centerCrop()
                .into(binding.imgPosterThumnail)
            Glide
                .with(this)
                .load(it.poster)
                .centerCrop()
                .into(binding.imgPosterD)

            binding.txtRelease.text = it.released
            binding.txtRuntime.text = it.runtime
            binding.txtImdbRating.text = it.imdbRating
            binding.txtImdbVotes.text = it.imdbVotes
            val genresSize = it.genres.size
            if ( genresSize == 1 ) {
                binding.txtGenres1.text = it.genres[0]
                binding.txtGenres2.visibility = View.GONE
                binding.txtGenres3.visibility = View.GONE
            }
            if ( genresSize == 2 ) {
                binding.txtGenres1.text = it.genres[0]
                binding.txtGenres2.text = it.genres[1]
                binding.txtGenres3.visibility = View.GONE
            }
            if ( genresSize == 3 ) {
                binding.txtGenres1.text = it.genres[0]
                binding.txtGenres2.text = it.genres[1]
                binding.txtGenres3.text = it.genres[2]
            }
            binding.txtRated.text = it.rated
            binding.txtplot.text = it.plot
            binding.txtDirector.text = it.director
            binding.txtWriter.text = it.writer
            binding.txtActors.text = it.actors
            if ( genresSize == 1 ) {
                binding.txtGenres11.text = it.genres[0]
                binding.txtGenres22.visibility = View.GONE
                binding.txtGenres33.visibility = View.GONE
            }
            if ( genresSize == 2 ) {
                binding.txtGenres11.text = it.genres[0]
                binding.txtGenres22.text = it.genres[1]
                binding.txtGenres33.visibility = View.GONE
            }
            if ( genresSize == 3 ) {
                binding.txtGenres11.text = it.genres[0]
                binding.txtGenres22.text = it.genres[1]
                binding.txtGenres33.text = it.genres[2]
            }
            binding.txtRuntime1.text = it.runtime
            binding.txtRelease1.text = it.released
            binding.txtAwards.text = it.awards
            binding.txtCountries.text = it.country
            binding.txtCertificate.text = it.rated
            binding.txtType.text = it.type
            binding.txtYear1.text = it.year





            it.images.let {response->
                viewPagerAdapter = ImageSlideAdapter(requireContext(), response)
                binding.viewpager.adapter = viewPagerAdapter
                indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
                indicator.setViewPager(binding.viewpager)
            }
        }

        return binding.root
    }


}