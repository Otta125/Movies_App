package com.yassir.moviesapp.Features.ShowMovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yassir.moviesapp.API.Api
import com.yassir.moviesapp.Model.MoviesModel.Movies
import com.yassir.moviesapp.paging.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject
constructor(
    myApplicationContext: Application,
    private val api: Api,
    private val moviesList: MutableLiveData<PagingData<Movies>>
) : AndroidViewModel(myApplicationContext) {

    fun getMoviesList(): LiveData<PagingData<Movies>> {
        val response = Pager(PagingConfig(pageSize = 1)) {
            MoviesPagingSource(api)
        }.liveData.cachedIn(viewModelScope)
        moviesList.value = response.value
        return response
    }
}