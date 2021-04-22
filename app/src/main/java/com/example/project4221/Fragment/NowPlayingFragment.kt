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
import com.khtn.androidcamp.DataCenter.Companion.getNowPlayingMovieJson
import retrofit2.Call
import retrofit2.Response


class NowPlayingFragment(val Type: Boolean, val database: MoviesDatabase) : BaseFragment() {

    override fun getLoggerTag(): String {
        return NowPlayingFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(getLoggerTag(), "onCreateView")
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNowPlaying = view.findViewById<RecyclerView>(R.id.rvNowPlaying)
        val adapter: MovieAdapter

        if(Type) {
            adapter = MovieAdapter(
                requireActivity(),
                getDataFromApi(),
                0, database
            )
            rvNowPlaying.layoutManager = GridLayoutManager(activity,3)
        } else {
            adapter = MovieAdapter(
                requireActivity(),
                getDataFromApi(),
                1,
                database)
            rvNowPlaying.layoutManager = LinearLayoutManager(activity)
        }
        rvNowPlaying.adapter = adapter
        adapter.Listener = object :
            MovieAdapter.MoviesListener {
            override fun onClickListener(movies: Movies) {
                startDetailScreen(movies)
            }
        }
    }

    fun ConvertJsonToList(): ArrayList<Movies> {
        var movies = ArrayList<Movies>()
        val dates: Result = Gson().fromJson(getNowPlayingMovieJson(), Result::class.java)
        dates.results?.let { movies.addAll(it) }
        return movies
    }

    companion object{
        var listMovie = ArrayList<Movies>()
    }

    private fun getDataFromApi(): ArrayList<Movies> {
        MovieService.getApi().getNowPlaying().enqueue(object : retrofit2.Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.e("MainActivity", "Problem calling Github API ${t?.message}")
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                response?.let {
                    val resp = it.body()
                    Log.e("TAG", "ok ok ok ${resp?.results}")

                    for (e: Movies in resp?.results!!){
                        val mv = Movies()
                        mv.id = e.id
                        mv.title = e.title
                        mv.overview = e.overview
                        mv.poster_path = e.poster_path
                        listMovie.add(mv)
                    }
                }
            }

        })
        return listMovie
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