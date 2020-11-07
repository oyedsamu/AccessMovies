package com.decadevs.accessmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.data.MoviePhoto
import com.decadevs.accessmovies.databinding.ItemMoviesBinding

class MoviePhotoAdapter (private val listener : OnItemClickListener) : PagingDataAdapter<MoviePhoto, MoviePhotoAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    inner class MovieViewHolder (private val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (movie : MoviePhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.photo)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(itemMovieImage)

                itemMovieTitle.text = movie.name
                itemMovieGenre.text = movie.genre
                itemMovieRating.text = movie.rating
                itemPriceTicket.text = movie.rating
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(movie: MoviePhoto)
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

        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false )

        return MovieViewHolder(binding)
    }


}