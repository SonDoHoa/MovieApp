package com.example.project4221

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.project4221.Model.MOVIE_DESCRIPTION_KEY
import com.example.project4221.Model.MOVIE_IMAGE_BACKDROP_KEY
import com.example.project4221.Model.MOVIE_TITLE_KEY
import com.example.project4221.Model.MOVIE_VOTE_AVERAGE
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        var ratingBar = findViewById<RatingBar>(R.id.vote_average)
        var name = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        var description = findViewById<TextView>(R.id.overview)
        var backdrop = findViewById<ImageView>(R.id.poster_movie)

        var intent = getIntent()
        if (intent!=null){
            name.title = intent.getStringExtra(MOVIE_TITLE_KEY)
            description.text = intent.getStringExtra(MOVIE_DESCRIPTION_KEY)
            ratingBar.rating = intent.getFloatExtra(MOVIE_VOTE_AVERAGE, 5.0f)
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+ intent.getStringExtra(
                    MOVIE_IMAGE_BACKDROP_KEY
                ))
                .into(backdrop)
        }
    }
}