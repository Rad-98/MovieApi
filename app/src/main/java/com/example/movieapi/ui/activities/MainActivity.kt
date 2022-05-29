package com.example.movieapi.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.movieapi.R
import com.example.movieapi.databinding.ActivityMainBinding
import com.example.movieapi.ui.fragments.FavoriteFragment
import com.example.movieapi.ui.fragments.HomeFragment
import com.example.movieapi.ui.fragments.ProfileFragment
import com.example.movieapi.ui.fragments.SearchFragment
import com.example.movieapi.viewmodels.MovieViewModel
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var bottomNav: ChipNavigationBar
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        bottomNav = binding.chipNavBar

        if (savedInstanceState == null){
            bottomNav.setItemSelected(R.id.homeMenu,true)
            fragmentManager = supportFragmentManager
            fragment = HomeFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit()

        }
        if (savedInstanceState!=null){
            fragment = supportFragmentManager.getFragment(savedInstanceState,"homeFragment")!!
        }

        bottomNav.setOnItemSelectedListener(object :ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(id: Int) {
                when(id){
                    R.id.homeMenu ->
                        fragment = HomeFragment()
                    R.id.search ->
                        fragment = SearchFragment()
                    R.id.favorite ->
                        fragment = FavoriteFragment()
                    R.id.profile ->
                        fragment = ProfileFragment()
                }

                fragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .addToBackStack(null)
                    .commit()
            }

        })

    }


}