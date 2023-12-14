package com.navin.filimo.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.navin.filimo.R
import com.navin.filimo.models.Video
import com.navin.filimo.ui.player.VideoPlayerActivity
import com.squareup.picasso.Picasso

class VideoAdapter(mContext: Activity, data: MutableList<Video>) :
    RecyclerView.Adapter<VideoAdapter.VideoVH>() {
    val context: Activity = mContext
    val videoList = data

    class VideoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgVideo = itemView.findViewById<AppCompatImageView>(R.id.img_video)
        val txtTitle = itemView.findViewById<AppCompatTextView>(R.id.txt_title)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.video_row, null)
        return VideoVH(view)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {

        val video : Video = videoList.get(position)

        holder.txtTitle.setText(video.video_title)

        Log.e("video","${video.video_thumbnail_s}")
        Picasso.get().load(video.video_thumbnail_s).into(holder.imgVideo)


        val typeFaceIRANSans = Typeface.createFromAsset(context.assets,"fonts/IRANSans.ttf")
        holder.txtTitle.setTypeface(typeFaceIRANSans)

        holder.cardView.setOnClickListener {


            val intent = Intent(context , VideoPlayerActivity::class.java)
            intent.putExtra("videoObj",video)
            context.startActivity(intent)
        }


    }

}