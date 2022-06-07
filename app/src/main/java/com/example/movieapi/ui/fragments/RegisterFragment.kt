package com.example.movieapi.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movieapi.api.models.user.User
import com.example.movieapi.databinding.FragmentRegisterBinding
import com.example.movieapi.viewmodels.users.UserViewModel
import com.google.gson.Gson

class RegisterFragment : Fragment() {

    private lateinit var binding :FragmentRegisterBinding
    private lateinit var viewModel:UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]



        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailR.text.toString()
            val name = binding.erUsernameR.text.toString()
            val password = binding.etPasswordR.text.toString()
            val user =  User(email = email, name = name, password = password)

            viewModel.registerUser(user)
            viewModel.registerResult.observe(viewLifecycleOwner){
                Log.i("REGISTER",it.toString())
            }
        }

        return binding.root
    }

}