package com.decadevs.accessmovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.ItemMoviesBinding
import java.util.*

class MovieAdapter(val movies: MutableList<Movie>, var listener: OnItemClick) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    /** FILTER RECYCLER VIEW FOR SEARCH */
    var moviesFilterList = mutableListOf<Movie>()
    init {
        moviesFilterList = movies
    }

    /** GET FILTERED MOVIES */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {

                val charSearch = p0.toString()
                if(charSearch.isEmpty()) {
                    moviesFilterList = movies
                } else {
                    val resultList = mutableListOf<Movie>()
                    for(row in movies) {
                        if(row.title.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    moviesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                moviesFilterList = p1?.values as MutableList<Movie>
                notifyDataSetChanged()
            }
        }
    }

    class MovieViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var movie: Movie

        fun bind(movie: Movie, action: OnItemClick) {
            this.movie = movie
            binding.apply {
                Glide.with(itemView)
                    .load(R.drawable.sixunderground)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(itemMovieImage)

                itemMovieTitle.text = movie.title
                itemMovieGenre.text = movie.genre
                itemMovieRating.text = movie.rating
                itemPriceTicket.text = movie.ticketPrice
            }

            itemView.setOnClickListener {
                action.onItemClick(movie, adapterPosition)
            }
        }
     }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = moviesFilterList[position]
        holder.bind(currentItem, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = moviesFilterList.size

    fun update(list: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(list)
        notifyDataSetChanged()
    }

}