package com.mbh.moviebrowser.features.movieDetails

import androidx.lifecycle.ViewModel
import com.mbh.moviebrowser.domain.movie.Movie
import com.mbh.moviebrowser.store.movie.MovieStore
import kotlinx.coroutines.flow.Flow

class MovieDetailsViewModel(private val movieStore: MovieStore) : ViewModel() {

    val movie: Flow<Movie?> = movieStore.selectedMovie

    fun onFavoriteClicked(isFavorite: Boolean) {
        movieStore.updateMovies(
            movieStore.movies.value.map { movie ->
                if (movie.id == movieStore.selectedMovie.value?.id) {
                    movie.copy(isFavorite = isFavorite).also {
                        movieStore.updateSelectedMovie(it)
                    }
                }
                else movie
            }
        )
    }
}
