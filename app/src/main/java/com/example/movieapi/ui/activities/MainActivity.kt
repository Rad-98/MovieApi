package com.example.movieapi.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movieapi.R
import com.example.movieapi.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragment: Fragment


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        val bottomNav = binding.bottomNavigation
        val navController = findNavController(R.id.my_nav_host_fragment)

        val configToolbar = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController,configToolbar)
        bottomNav.setupWithNavController(navController)


    }

}