package com.navin.filimo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

     var retrofit = Retrofit.Builder()
         .baseUrl("https://mobilemasters.ir/apps/filimo-android/")
         .addConverterFactory(GsonConverterFactory.create())
          .build()

}