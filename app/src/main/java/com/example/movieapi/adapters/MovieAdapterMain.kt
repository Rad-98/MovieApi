package com.example.movieapi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.api.models.movies.DataM
import com.example.movieapi.database.entity.movie.MovieBookmark
import com.example.movieapi.databinding.ItemListMovieBinding
import com.example.movieapi.databinding.ItemListMovieHorizontalBinding
import kotlin.collections.ArrayList


class MovieAdapterMain(private val context: Context?) :
    RecyclerView.Adapter<MovieAdapterMain.ViewHolder>() {

    private var dataList = ArrayList<DataM>()
    private lateinit var onItemClickListener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterMain.ViewHolder {
        val view = ItemListMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieAdapterMain.ViewHolder, position: Int) {

        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(dataList[position])
        }



    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllMovie(list: List<DataM>,sort:String) {

        when(sort){
            "NEW" ->{
                dataList.clear()
                dataList.addAll(list.sortedBy { it.year }.reversed())
                notifyDataSetChanged()
            }
            "IMDB"->{
                dataList.clear()
                dataList.addAll(list.sortedBy { it.imdb_rating }.reversed())
                notifyDataSetChanged()
            }
            "ID"->{
                dataList.clear()
                dataList.addAll(list.sortedBy { it.id })
                notifyDataSetChanged()
            }
        }

    }




    inner class ViewHolder(
        private val binding: ItemListMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DataM) {
            binding.txtTitle.text = item.title
            binding.txtIMBD.text = item.imdb_rating
            Glide
                .with(context!!)
                .load(item.poster)
                .centerCrop()
                .into(binding.imgPoster)

        }
    }



    interface OnItemClickListener {
        fun onItemClick(dataM: DataM)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


}