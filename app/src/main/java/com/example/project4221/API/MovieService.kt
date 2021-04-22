package com.example.project4221.API

import com.example.project4221.MainActivity
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieService {

    companion object{
        private var api: MovieApi? = null

        fun getApi() : MovieApi = api
                ?: synchronized(this){
            api
                    ?: createInstance().also { api = it }
        }

        private fun createInstance() : MovieApi {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
                    .build()

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            return retrofit.create(MovieApi::class.java)
        }
    }
}