package com.yassir.moviesapp.Features.MovieDetails

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.yassir.moviesapp.R
import com.yassir.moviesapp.Utilities.Const
import com.yassir.moviesapp.Utilities.Const.MOVIE_DETAILS_INTENT_ID
import com.yassir.moviesapp.Utilities.LoadingDialog
import com.yassir.moviesapp.Utilities.Status
import com.yassir.moviesapp.databinding.ActivityMoviesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesDetailsBinding
    private lateinit var loadingDialog: LoadingDialog

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        binding = ActivityMoviesDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle: Bundle? = intent.extras
        val id = intent.getIntExtra(MOVIE_DETAILS_INTENT_ID, 0)

        ///
        loadingDialog = LoadingDialog(this)

        movieDetailsViewModel.getMovieDetails(id)

        movieDetailsViewModel.moviesLiveData.observe(this) { movieDetailsResponse ->
            if (movieDetailsResponse?.data == null) {
                return@observe
            }
            when (movieDetailsResponse.status) {
                Status.SUCCESS -> {
                    loadingDialog.dismissDialog()
                    val releaseYear = movieDetailsResponse.data.release_date
                    if (releaseYear.split('-').isEmpty()) {
                        return@observe
                    }
                    val year = releaseYear.split('-')[0]
                    binding.apply {
                        tvTitle.text = movieDetailsResponse.data.title
                        tvYear.text = year
                        tvDetails.text = movieDetailsResponse.data.overview
                        Picasso.get()
                            .load(Const.ROW_MOVIE_POSTER + movieDetailsResponse.data.poster_path)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .fit()
                            .into(ivPosterImage)
                    }
                }
                Status.LOADING -> {
                    loadingDialog.startLoadingDialog()
                }
                Status.ERROR -> {
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this,
                        movieDetailsResponse.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}