package com.navin.filimo.api

import com.navin.filimo.models.CommentDataModel
import com.navin.filimo.models.HomeData
import com.navin.filimo.models.LoginModel
import com.navin.filimo.models.VideoModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IService {

    @GET("api.php?home_videos")
    fun getHome(): Call<ResponseBody>

    @GET("api.php?home_videos")
    fun getHomeData(): Call<HomeData>

    @GET("api.php?users_login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<LoginModel>


    @GET("api.php?user_register")
    fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Call<LoginModel>

    @GET("api.php")
    fun video(
        @Query("video_id") videoId: String
    ): Call<VideoModel>


    @GET("api.php?video_comment")
    fun videoComment(
        @Query("comment_text") commentText: String,
        @Query("user_name") username: String,
        @Query("post_id") postId: String
    ): Call<CommentDataModel>



}