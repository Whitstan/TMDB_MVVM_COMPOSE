package com.mbh.moviebrowser.features.movieList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mbh.moviebrowser.domain.movie.Movie
import com.mbh.moviebrowser.remote.api.TMDBRetrofitClient
import com.mbh.moviebrowser.store.genre.GenreStore
import com.mbh.moviebrowser.store.movie.MovieStore

class MoviePagingSource(
    private val retrofitClient: TMDBRetrofitClient,
    private val genreStore: GenreStore,
    private val movieStore: MovieStore
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val currentPage = params.key ?: 1

            if (currentPage <= movieStore.getLoadedPage()) {
                return LoadResult.Page(
                    data = movieStore.movies.value,
                    prevKey = null,
                    nextKey = movieStore.getLoadedPage() + 1
                )
            }


            val moviesList = retrofitClient.api.fetchTrendingMovies(currentPage).results.map { movieDTO ->
                val genres = movieDTO.genreIds.mapNotNull { genreId ->
                    genreStore.genres.value.find { it.id == genreId }
                }

                Movie(
                    id = movieDTO.id,
                    title = movieDTO.title,
                    genres = genres.joinToString { it.name },
                    overview = movieDTO.overview,
                    coverUrl = movieDTO.posterPath,
                    rating = movieDTO.voteAverage,
                    isFavorite = movieStore.movies.value.find { it.id == movieDTO.id }?.isFavorite ?: false
                )
            }

            movieStore.updateMovies(movieStore.movies.value + moviesList)
            movieStore.updateLoadedPage(currentPage)

            return LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (moviesList.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null

}