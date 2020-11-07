package com.decadevs.accessmovies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.data.MoviePhoto
import com.decadevs.accessmovies.databinding.ItemMoviesBinding

class MoviePhotoAdapter : PagingDataAdapter<MoviePhoto, MoviePhotoAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    inner class MovieViewHolder (private val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (movie : MoviePhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.photo)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error()
            }
        }

    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MoviePhoto>() {
            override fun areItemsTheSame(oldItem: MoviePhoto, newItem: MoviePhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MoviePhoto, newItem: MoviePhoto): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null ) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        TODO("Not yet implemented")
    }


}