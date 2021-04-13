package com.example.project4221.Model

import android.os.Parcelable
import androidx.room.Ignore
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.khtn.androidcamp.DataCenter
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.ArrayList

const val MOVIE_TITLE_KEY = "MOVIE_TITLE_KEY"
const val MOVIE_DESCRIPTION_KEY = "MOVIE_DESCRIPTION_KEY"
const val MOVIE_IMAGE_BACKDROP_KEY = "MOVIE_IMAGE_BACKDROP_KEY"
const val MOVIE_VOTE_AVERAGE = "MOVIE_VOTE_AVERAGE"

data class Movies(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    var id: Int?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    var overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("release_date") val releaseDate: String?,
    var title: String?,
    val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
){
    constructor() : this(null,null,null,null,null,null, null,null, null, null, null, null, null, null)
}
