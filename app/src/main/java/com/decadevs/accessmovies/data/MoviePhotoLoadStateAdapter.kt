package com.decadevs.accessmovies.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.accessmovies.databinding.MoviePhotoLoadStateFooterBinding

class MoviePhotoLoadStateAdapter (private val retry : () -> Unit) : LoadStateAdapter<MoviePhotoLoadStateAdapter.LoadStateViewHolder> () {


    inner class LoadStateViewHolder (private val binding: MoviePhotoLoadStateFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind ( loadState: LoadState) {

            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = MoviePhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }
}