package com.mbh.moviebrowser.remote.api

import com.mbh.moviebrowser.remote.dto.genre.GenresResponse
import com.mbh.moviebrowser.remote.dto.movie.TrendingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBRetrofitApi {
    @GET("genre/movie/list")
    suspend fun fetchGenres(): GenresResponse

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovies(@Query("page") page: Int): TrendingMoviesResponse
}