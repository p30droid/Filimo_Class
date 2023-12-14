package com.navin.filimo.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.navin.filimo.R
import com.navin.filimo.models.Comment

class CommentsAdapter(activity: Activity, data: MutableList<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentsVH>() {

    val context = activity
    val commentsList = data


    class CommentsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtUsername = itemView.findViewById<AppCompatTextView>(R.id.txt_username)
        val txtComment = itemView.findViewById<AppCompatTextView>(R.id.txt_comment)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsVH {
        val view = LayoutInflater.from(context).inflate(R.layout.comments_row, null)
        return CommentsVH(view)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: CommentsVH, position: Int) {

        val comment = commentsList.get(position)
        holder.txtComment.setText(comment.commentText)
        holder.txtUsername.setText(comment.username)


    }
}