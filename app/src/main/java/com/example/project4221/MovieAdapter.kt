package com.example.project4221

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.project4221.Model.Movies
import com.example.project4221.Room.MoviesDatabase
import com.example.project4221.Room.MoviesEntity
import androidx.appcompat.app.AlertDialog as AlertDialog1

class MovieAdapter(val ctx: Context, val movies: ArrayList<Movies>, var type: Int, val db: MoviesDatabase) : RecyclerView.Adapter<MovieAdapter.MovieVH>(){
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        var view = LayoutInflater.from(ctx).inflate(R.layout.mv_item,parent,false)
        if (type==0){
            view = LayoutInflater.from(ctx).inflate(R.layout.mv_grid_item,parent,false)
        }
        return MovieVH(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val movie = movies[position]

        if (type == 0){
            holder.tvName.text = movie.title
            Glide.with(ctx)
                    .load("https://image.tmdb.org/t/p/w500"+movie.poster_path)
                    .into(holder.ivAvatar)
        }else {
            holder.tvName.text = movie.title
            holder.tvDescription.text = movie.overview
            Glide.with(ctx)
                    .load("https://image.tmdb.org/t/p/w500"+movie.poster_path)
                    .into(holder.ivAvatar)
        }

        holder.itemView.setOnClickListener{
            Listener?.onClickListener(movie)
        }

        var flag: Boolean = false
        holder.ivMyFav.setOnClickListener{
            if (holder.ivMyFav.isClickable && flag == false) {
                holder.ivMyFav.setImageResource(R.drawable.ic_baseline_star_rate_24)
                ShowAlertDialog(movie, holder)
                flag = true
            }else if(holder.ivMyFav.isClickable && flag == true){
                holder.ivMyFav.setImageResource(R.drawable.ic_baseline_star_rate_white_24)
                flag = false
            }
        }
    }

    var listFavorite:ArrayList<Movies> = ArrayList()
    fun ShowAlertDialog(movie: Movies, holder: MovieVH){
        var favorite_movie = MoviesEntity()

        val builder = AlertDialog1.Builder(ctx)
        builder.setMessage(R.string.save_movie_to_myfav)
                .setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, id ->
                            holder.ivMyFav.setImageResource(R.drawable.ic_baseline_star_rate_24)
                            favorite_movie.name = movie.title
                            favorite_movie.description = movie.overview
                            favorite_movie.backdropPath = movie.backdropPath
                            db.movieDAO().insert(favorite_movie)
                            dialog.dismiss()
                        })
                .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            holder.ivMyFav.setImageResource(R.drawable.ic_baseline_star_rate_white_24)
                            db.movieDAO().delete(favorite_movie)
                            Toast.makeText(ctx, "Delete favorite movie", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        })
        builder.create()
                .show()
    }

    var Listener: MoviesListener? = null
    interface MoviesListener{
        fun onClickListener(movies: Movies)
    }

    class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivAvatar = itemView.findViewById<ImageView>(R.id.mvImage)
        val tvName = itemView.findViewById<TextView>(R.id.mvname)
        val tvDescription = itemView.findViewById<TextView>(R.id.mvDescription)
        val ivMyFav = itemView.findViewById<ImageView>(R.id.btnFav)
    }

}
