package com.decadevs.accessmovies.adapters

import com.decadevs.accessmovies.data.Movie

interface OnItemClick {
    fun onItemClick(item: Movie, position:Int)
}