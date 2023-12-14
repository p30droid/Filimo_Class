package com.navin.filimo.models

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("ALL_IN_ONE_VIDEO")
    val login: List<User>
)