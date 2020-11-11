package com.decadevs.accessmovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.databinding.CommentItemBinding

class CommentRecycler(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentRecycler.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class MyViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var comment: Comment

        fun bind(comment: Comment) {
            this.comment = comment
            binding.apply {
                commentNameText.text = comment.user
                commentBodyText.text = comment.comment
            }
        }
    }
}