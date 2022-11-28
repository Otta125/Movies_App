package com.yassir.moviesapp.Features.ShowMovies

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yassir.moviesapp.Features.Adapters.MoviesAdapter
import com.yassir.moviesapp.R
import com.yassir.moviesapp.Utilities.LoadingDialog
import com.yassir.moviesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var moviesAdapter: MoviesAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadingDialog = LoadingDialog(this)
        moviesAdapter = MoviesAdapter(this)
        binding.moviesRec.apply {
            adapter = moviesAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }

        moviesViewModel.getMoviesList().observe(this) {
            lifecycleScope.launch {
                moviesAdapter.submitData(it)
            }
        }
        moviesAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                loadingDialog.dismissDialog()
            }
            if (it.refresh is LoadState.Loading) {
                loadingDialog.startLoadingDialog()
            } else {
                loadingDialog.dismissDialog()
            }
        }
    }
}