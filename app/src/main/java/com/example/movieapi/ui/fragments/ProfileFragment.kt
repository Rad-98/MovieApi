package com.example.movieapi.ui.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.movieapi.R
import com.example.movieapi.databinding.FragmentProfileBinding
import com.example.movieapi.viewmodels.users.UserViewModel


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController
    private lateinit var viewModel:UserViewModel

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
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.txtGoToRegister.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {

            var grant_type = "password"
            var username = binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()


            viewModel.login(grant_type,username,password)
            viewModel.registerResult.observe(viewLifecycleOwner){
                Log.i("LOGIN",it.toString())
            }
        }












        return binding.root
    }


}