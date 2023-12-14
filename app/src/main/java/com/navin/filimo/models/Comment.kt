package com.navin.filimo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    @SerializedName("video_id")
    val videoId : String? = "",
    @SerializedName("user_name")
    val username : String? = "",
    @SerializedName("comment_text")
    val commentText : String? = ""
): Parcelable
