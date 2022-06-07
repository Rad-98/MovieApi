package com.example.movieapi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.api.models.movies.Genres
import com.example.movieapi.databinding.ItemGenresBinding

class GenresAdapter constructor(private val context: Context) :
    RecyclerView.Adapter<GenresAdapter.viewHolder>() {

    private val dataList =  ArrayList<Genres>()
    private var onItemClickListener: OnItemClicklistener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.setOnItemClick(dataList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllGenres(list: List<Genres>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    inner class viewHolder(private val binding: ItemGenresBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Genres) {
            binding.txtGenTitle.text = item.name
        }

    }

    interface OnItemClicklistener{
        fun setOnItemClick(genresId:Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClicklistener){
        this.onItemClickListener = onItemClickListener
    }

}