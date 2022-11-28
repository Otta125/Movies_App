package com.yassir.moviesapp.DI

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.yassir.moviesapp.API.Api
import com.yassir.moviesapp.API.RetrofitClient
import com.yassir.moviesapp.Model.MovieDetailsModel.MovieDetailsResponse
import com.yassir.moviesapp.Model.MoviesModel.Movies
import com.yassir.moviesapp.Model.MoviesModel.MoviesResponse
import com.yassir.moviesapp.Utilities.Resource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMovies(): MutableLiveData<PagingData<Movies>> = MutableLiveData()


    @Provides
    fun provideMovieDetails(): MutableLiveData<Resource<MovieDetailsResponse>> =
        MutableLiveData()

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Api = RetrofitClient.instance


    @Singleton
    @Provides
    fun provideApplicationContext() = MyApplication()


}