package com.decadevs.accessmovies

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.accessmovies.data.MoviePhoto
import com.decadevs.accessmovies.databinding.ItemMoviesBinding

class MoviePhotoAdapter : PagingDataAdapter<MoviePhoto, MoviePhotoAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    inner class MovieViewHolder (private val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MoviePhoto>() {
            override fun areItemsTheSame(oldItem: MoviePhoto, newItem: MoviePhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MoviePhoto, newItem: MoviePhoto): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}