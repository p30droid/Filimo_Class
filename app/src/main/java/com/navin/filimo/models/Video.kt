package com.navin.filimo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id : String,
    val cat_id : String,
    val video_type : String,
    val video_title : String,
    val video_url : String,
    val video_id : String,
    val video_thumbnail_b : String,
    val video_thumbnail_s : String,
    val video_duration : String,
    val video_description : String,
    val rate_avg : String,
    val totel_viewer : String,
    val cid : String,
    val category_name : String,
    val category_image : String,
    val category_image_thumb : String,
    val related : List<Video>? = arrayListOf(),
    @SerializedName("user_comments")
    val userComments : MutableList<Comment>? = arrayListOf()
):Parcelable
