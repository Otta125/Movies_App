package com.yassir.moviesapp.API


import com.yassir.moviesapp.Model.MovieDetailsModel.MovieDetailsResponse
import com.yassir.moviesapp.Model.MoviesModel.MoviesResponse
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<MoviesResponse?>?


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String

    ): Response<MovieDetailsResponse?>?
}