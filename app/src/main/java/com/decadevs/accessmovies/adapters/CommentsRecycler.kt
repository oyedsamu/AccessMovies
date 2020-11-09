package com.decadevs.accessmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Comment
import java.util.*

class CommentRecycler(val context: Context, private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentRecycler.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = comments[position]
        holder.setData(comment, position)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var currentComments: Comment? = null
        var currentPos = 0
        var currentId = 0

        fun setData(comments: Comment, position: Int) {
//            itemView.comm.text = comments.name.toUpperCase(Locale.ROOT)
//            itemView.comment_body_text.text = comments.body
//            itemView.comment_email_text.text = comments.email
//
//            this.currentComments = comments
//            this.currentPos = position
//            this.currentId = comments.commentId
        }
    }

    private fun shortenString(item: String): String{
        if(item.length <= 40 ) return item
        return  item.substring(0, 39)
    }


}