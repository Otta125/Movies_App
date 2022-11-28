package com.yassir.moviesapp.Features.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yassir.moviesapp.Features.MovieDetails.MovieDetailsActivity
import com.yassir.moviesapp.Model.MoviesModel.Movies
import com.yassir.moviesapp.R
import com.yassir.moviesapp.Utilities.Const
import com.yassir.moviesapp.Utilities.Const.ROW_MOVIE_POSTER
import com.yassir.moviesapp.databinding.MovieRowBinding

class MoviesAdapter(
    private val mCx: Context
) : PagingDataAdapter<Movies, MoviesAdapter.MoviesViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.binding.apply {
            holder.itemView.apply {
                Picasso.get()
                    .load(ROW_MOVIE_POSTER + movie.poster_path)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(ivPosterImage)
                ivPosterImage.setOnClickListener {
                    try {
                        val intent = Intent(mCx, MovieDetailsActivity::class.java)
                        intent.putExtra(Const.MOVIE_DETAILS_INTENT_ID, movie.id)
                        val bundle: Bundle? = intent.extras
                        ContextCompat.startActivity(mCx, intent, bundle)
                    } catch (e: Exception) {
                        Log.e("ERROR", e.message.toString())
                    }
                }
            }
        }
    }

    class MoviesViewHolder(val binding: MovieRowBinding) : RecyclerView.ViewHolder(binding.root)
}