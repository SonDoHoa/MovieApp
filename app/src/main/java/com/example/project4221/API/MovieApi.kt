package com.example.project4221.API

import com.example.project4221.Model.Result
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRating(): Call<Result>

    @GET("movie/now_playing")
    fun getNowPlaying(): Call<Result>
}