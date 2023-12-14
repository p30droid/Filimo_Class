package com.navin.filimo.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("success")
    val success: String,
    @SerializedName("user_id")
    val userId: String
)
