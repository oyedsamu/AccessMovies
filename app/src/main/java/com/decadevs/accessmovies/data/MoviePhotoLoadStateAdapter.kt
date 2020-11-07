package com.decadevs.accessmovies.data

import android.view.ViewGroup
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

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        TODO("Not yet implemented")
    }
}