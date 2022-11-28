package com.yassir.moviesapp.Features.MovieDetails

import com.yassir.moviesapp.Model.MovieDetailsModel.MovieDetailsResponse
import android.app.Application
import androidx.lifecycle.*

import com.yassir.moviesapp.API.RetrofitClient
import com.yassir.moviesapp.R
import com.yassir.moviesapp.Utilities.ConnectionDetector
import com.yassir.moviesapp.Utilities.Const.AUTH_KEY
import com.yassir.moviesapp.Utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val myApplicationContext: Application,
     val moviesLiveData: MutableLiveData<Resource<MovieDetailsResponse>>
) : AndroidViewModel(myApplicationContext) {

    fun getMovieDetails(movieId: Int) {
        if (!ConnectionDetector.checkForInternet(myApplicationContext)) {
            moviesLiveData.postValue(
                Resource.error(
                    myApplicationContext.getString(R.string.no_connection),
                    null
                )
            )
            return
        }
        viewModelScope.launch {
            moviesLiveData.postValue(Resource.loading(null))
            RetrofitClient.instance.getMovieDetails(movieId.toString(),AUTH_KEY).let {
                if (it!!.isSuccessful) {
                   moviesLiveData.postValue(Resource.success(it.body()))
                } else {
                    moviesLiveData.postValue(
                        Resource.error(
                            myApplicationContext.getString(R.string.try_again),
                            null
                        )
                    )
                }
            }
        }
    }
}