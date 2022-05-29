package com.example.movieapi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.database.models.DataM
import com.example.movieapi.databinding.ItemListMovieBinding


class MovieAdapter constructor(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var dataList = ArrayList<DataM>()
    private lateinit var onItemClickListener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllMovie(list: List<DataM>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DataM) {
            binding.txtTitle.text = item.title
            val genres = item.genres.toString().replace("[", "").replace("]", "")
            binding.txtGenres.text = genres
            binding.txtcountryAndYear.text = item.country + " - " + item.year
            binding.txtIMBD.text = item.imdb_rating

            Glide
                .with(context)
                .load(item.poster)
                .centerCrop()
                .into(binding.imgPoster)

        }
    }

    interface OnItemClickListener{
        fun onItemClick(dataM: DataM)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }


}