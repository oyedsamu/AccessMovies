package com.decadevs.accessmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.databinding.CommentItemBinding
import com.decadevs.accessmovies.databinding.ItemMoviesBinding

class CommentRecycler(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentRecycler.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment, position)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

//    inner class MyViewHolder(binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var comm: Comment? = null
//        var currentPos = 0
//        var currentId = 0
//
//        fun setData(comment: Comment, position: Int) {
//
//             .text = comment.user
//                commentBodyText.text = comment.comment
//        }
//   }

    class MyViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var comment: Comment

        fun bind(comment: Comment, position: Int) {
            this.comment = comment
            binding.apply {
                commentNameText.text = comment.user
                commentBodyText.text = comment.comment
            }
        }
    }
}