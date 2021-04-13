package com.example.project4221.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project4221.*
import com.example.project4221.Model.*
import com.example.project4221.Room.MoviesDatabase
import com.example.project4221.Room.MoviesEntity


class MyFavoriteFragment(val Type: Boolean, val database: MoviesDatabase) : BaseFragment(){
    override fun getLoggerTag(): String {
        return MyFavoriteFragment::class.java.simpleName
    }

    lateinit var adapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMyFavoriteFragment = view.findViewById<RecyclerView>(R.id.rvMyFavorite)
        if(Type) {
            adapter = MovieAdapter(
                requireActivity(),
                listMoviesFromDatabase(database.movieDAO().getAll()),
                0,
                database
            )
            rvMyFavoriteFragment.layoutManager = GridLayoutManager(activity,3)
        } else {
            adapter = MovieAdapter(
                requireActivity(),
                listMoviesFromDatabase(database.movieDAO().getAll()),
                1,
                database
            )
            rvMyFavoriteFragment.layoutManager = LinearLayoutManager(activity)
        }
        rvMyFavoriteFragment.adapter = adapter
        adapter.Listener = object :
            MovieAdapter.MoviesListener {
            override fun onClickListener(movies: Movies) {
                startDetailScreen(movies)
            }
        }
    }

    private fun listMoviesFromDatabase(mv: List<MoviesEntity>): ArrayList<Movies>{
        var mvArrayList =  ArrayList<Movies>()
        for (e:MoviesEntity in mv) {
            val mvAL = Movies()
            mvAL.id = e.id
            mvAL.title = e.name
            mvAL.overview = e.description
            mvAL.poster_path = e.backdropPath
            mvArrayList.add(mvAL)
        }
        return mvArrayList
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