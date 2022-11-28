package com.yassir.moviesapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yassir.moviesapp.API.Api
import com.yassir.moviesapp.Model.MoviesModel.Movies
import com.yassir.moviesapp.Model.MoviesModel.MoviesResponse
import com.yassir.moviesapp.Utilities.Const

class MoviesPagingSource(
    private val api: Api
) : PagingSource<Int, Movies>() {

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Movies> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getMovies(Const.AUTH_KEY, currentPage)
            val responseData = mutableListOf<Movies>()
            val data = response?.body()?.movies ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}