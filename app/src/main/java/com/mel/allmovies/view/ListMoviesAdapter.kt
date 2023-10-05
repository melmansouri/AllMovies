package com.mel.allmovies.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mel.allmovies.utilities.Constants
import com.mel.allmovies.R
import com.mel.allmovies.data.entities.Movie

class ListMoviesAdapter(private val listMovies: MutableList<Movie>): RecyclerView.Adapter<ListMoviesAdapter.MovieViewHolder>() {
    private val VIEW_ITEM_TYPE_LOADING = 1
    private val VIEW_ITEM_TYPE_DATA = 2

    override fun getItemId(position: Int): Long {
        return listMovies[position].id
    }

    /*override fun getItemViewType(position: Int): Int {
        return if (position >= listMovies.size -1) VIEW_ITEM_TYPE_LOADING else VIEW_ITEM_TYPE_DATA
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val idLayout = when(viewType){
            VIEW_ITEM_TYPE_LOADING -> R.layout.item_loading
            VIEW_ITEM_TYPE_DATA -> R.layout.item_movie
            else -> {
                R.layout.item_movie
            }
        }
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    fun updateMovies(moviesList: List<Movie>, isNewList: Boolean){
        if (isNewList){
            clearAddList(moviesList)
            notifyDataSetChanged()
            return
        }
        val lastPositionBeforeAddingMoreElements = listMovies.size
        val sizeElementsAdded = moviesList.size - listMovies.size
        clearAddList(moviesList)
        notifyItemRangeInserted(lastPositionBeforeAddingMoreElements, sizeElementsAdded)
    }

    private fun clearAddList(moviesList: List<Movie>){
        listMovies.clear()
        listMovies.addAll(moviesList)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie){
            val img = itemView.findViewById<ImageView>(R.id.imgMovie)
            val txtTitle = itemView.findViewById<TextView>(R.id.txtTitleMovie)
            txtTitle.text = movie.title
            Glide.with(img.context).load("${Constants.urlImage}${movie.poster_path}").placeholder(R.drawable.no_img_available).into(img)
        }
    }
}