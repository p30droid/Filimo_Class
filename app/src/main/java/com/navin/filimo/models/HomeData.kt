package com.navin.filimo.models

import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("ALL_IN_ONE_VIDEO")
    val baseModel : BaseVideo
)