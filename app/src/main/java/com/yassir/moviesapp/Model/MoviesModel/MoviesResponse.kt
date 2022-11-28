package com.yassir.moviesapp.Model.MoviesModel

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movies>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)