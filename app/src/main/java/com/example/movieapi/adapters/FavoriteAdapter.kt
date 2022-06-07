package com.example.movieapi.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapi.R
import com.example.movieapi.database.entity.movie.MovieBookmark
import com.example.movieapi.databinding.ItemListMovieBinding

class FavoriteAdapter constructor(private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var dataList = ArrayList<MovieBookmark>()
    private var onItemFavoriteClickListener : OnItemFavoriteClickListener? = null


    interface OnItemFavoriteClickListener{
        fun onFavoriteListener(data: MovieBookmark)
        fun onBookmarkIcomClicked(data: MovieBookmark)
    }

    fun setOnItemFavoriteClickListener(onFavoriteItemClickListener: OnItemFavoriteClickListener){
        onItemFavoriteClickListener = onFavoriteItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val v = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(v)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            onItemFavoriteClickListener?.onFavoriteListener(dataList[position])
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllFavorite(list: List<MovieBookmark>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(item: MovieBookmark){
                val id = item.movieId
                binding.txtTitle.text = item.title
                binding.txtTitle.textSize = 10f
                binding.txtGenres.textSize = 8f
                binding.txtGenres.text = "IMBD : ${item.imdb_rating}"
                binding.txtcountryAndYear.visibility = View.GONE
                binding.linearIMDB.visibility = View.GONE
                binding.bookmark.setImageResource(R.drawable.delete_outline_24)
                Glide
                    .with(context)
                    .load(item.poster)
                    .centerCrop()
                    .into(binding.imgPoster)

                binding.bookmark.setOnClickListener {
                    onItemFavoriteClickListener?.onBookmarkIcomClicked(item)
                }





            }


    }


}