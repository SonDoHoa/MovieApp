package com.example.project4221.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project4221.*
import com.example.project4221.API.MovieService
import com.example.project4221.Model.*
import com.example.project4221.Room.MoviesDatabase
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler

class TopRatingFragment(val Type: Boolean, val database: MoviesDatabase) : BaseFragment() {


    var listMovie = ArrayList<Movies>()

    override fun getLoggerTag(): String {
        return TopRatingFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDataFromApi()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val rvTopRating = view.findViewById<RecyclerView>(R.id.rvTopRate)
        val adapter: MovieAdapter

        if(Type) {
            adapter = MovieAdapter(
                requireActivity(),
                listMovie,
                0,
                database
            )
            rvTopRating.layoutManager = GridLayoutManager(activity,3)
        } else {
            adapter = MovieAdapter(
                requireActivity(),
                listMovie,
                1,
                database
            )
            rvTopRating.layoutManager = LinearLayoutManager(activity)
        }
        rvTopRating.adapter = adapter
        adapter.Listener = object :
            MovieAdapter.MoviesListener {
            override fun onClickListener(movies: Movies) {
                startDetailScreen(movies)
            }
        }
    }

    fun ConvertJsonToList(): ArrayList<Movies> {
        var movies = ArrayList<Movies>()
        val dates: Result = Gson().fromJson(DataCenter.getTopRateMovieJson(), Result::class.java)
        dates.results?.let { movies.addAll(it) }
        getDataFromApi()
        return movies
    }

    fun getDataFromApi(){
        MovieService.getApi().getTopRating().enqueue(object : retrofit2.Callback<Result> {
            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                //todo something
            }

            override fun onResponse(
                call: Call<Result>?,
                response: Response<Result>?
            ) {
                response?.let {
                    val resp = it.body()
                    Log.e("TAG", "data: ${resp?.results}")
//                    val dates: Result = Gson().fromJson(resp?.results, Result::class.java)
//                    dates.results?.let { listMovie.addAll(it) }
                }
            }

        })
    }

    private fun startDetailScreen(movies: Movies){
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_TITLE_KEY, movies.title)
        intent.putExtra(MOVIE_DESCRIPTION_KEY, movies.overview)
        intent.putExtra(MOVIE_IMAGE_BACKDROP_KEY, movies.backdropPath)
        intent.putExtra(MOVIE_VOTE_AVERAGE, movies.voteAverage?.div(2))
        startActivity(intent)
    }
}