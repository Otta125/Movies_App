package com.yassir.moviesapp.Model.MovieDetailsModel

import com.google.gson.annotations.SerializedName



data class Genres (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String
)