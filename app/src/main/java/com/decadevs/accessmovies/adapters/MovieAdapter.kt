package com.decadevs.accessmovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.ItemMoviesBinding

class MovieAdapter (val movies: MutableList<Movie>, var listener: OnItemClick) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){


     class MovieViewHolder (private val binding: ItemMoviesBinding) :  RecyclerView.ViewHolder(binding.root) {

         private lateinit var movie: Movie



        fun bind (movie : Movie, action: OnItemClick) {

            this.movie  = movie
            binding.apply {
                Glide.with(itemView)
                    .load(R.drawable.sixunderground)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(itemMovieImage)

                itemMovieTitle.text = movie.name
                itemMovieGenre.text = movie.genre
                itemMovieRating.text = movie.rating
                itemPriceTicket.text = movie.ticketPrice
            }

            itemView.setOnClickListener {
              action.onItemClick(movie, adapterPosition)
            }
        }


     }




//    companion object {
//        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
//            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
//                oldItem.id == newItem.id
//
//            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
//                oldItem.id == newItem.id
//        }
//    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movies[position]
        if (currentItem != null ) {
            holder.bind(currentItem, listener)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false )

        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size


    fun update(list: List<Movie>){
        this.movies.clear()
        this.movies.addAll(list)
        notifyDataSetChanged()
    }

}