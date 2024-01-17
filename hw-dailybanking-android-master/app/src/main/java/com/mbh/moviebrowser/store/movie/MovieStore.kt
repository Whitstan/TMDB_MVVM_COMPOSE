package com.mbh.moviebrowser.store.movie

import com.mbh.moviebrowser.domain.movie.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieStore {
    private val _selectedMovie: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val selectedMovie: StateFlow<Movie?>
        get() = _selectedMovie

    private val _movies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val movies: StateFlow<List<Movie>>
        get() = _movies

    fun updateMovies(updatedMovies: List<Movie>) {
        _movies.value = updatedMovies
    }

    fun updateSelectedMovie(movie: Movie?) {
        _selectedMovie.value = movie
    }

    private var loadedPage: Int = 0
    fun getLoadedPage() = loadedPage
    fun updateLoadedPage(page: Int) {
        loadedPage = page
    }
}
