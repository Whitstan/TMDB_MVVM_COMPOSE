package com.mbh.moviebrowser.features.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mbh.moviebrowser.domain.movie.Movie
import com.mbh.moviebrowser.store.genre.GenreStore
import com.mbh.moviebrowser.store.movie.MovieStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieStore: MovieStore,
    private val genreStore: GenreStore,
    private val pagingSource: MoviePagingSource
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = movieStore.movies.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flow.cachedIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            genreStore.fetchGenres()
        }
    }

    fun updateSelectedMovie(movie: Movie) {
        movieStore.updateSelectedMovie(movie)
    }
}
