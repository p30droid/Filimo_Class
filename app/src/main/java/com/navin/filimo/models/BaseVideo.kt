package com.navin.filimo.models

import com.google.gson.annotations.SerializedName

data class BaseVideo(
    @SerializedName("featured_video")
    val featuredVideo: MutableList<Video>,
    @SerializedName("latest_video")
    val latestVideo: MutableList<Video>,
    @SerializedName("all_video")
    val allVideo: MutableList<Video>,
    @SerializedName("category")
    val category: MutableList<Category>,
)
