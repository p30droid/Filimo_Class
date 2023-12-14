package com.navin.filimo.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("cid")
    val cid : String,
    @SerializedName("category_name")
    val categoryName : String,
    @SerializedName("category_image")
    val categoryImage : String,
    @SerializedName("category_image_thumb")
    val categoryImageThumb : String,
)
