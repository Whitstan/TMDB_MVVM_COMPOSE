package com.mbh.moviebrowser.store.genre

import com.mbh.moviebrowser.domain.genre.Genre
import com.mbh.moviebrowser.remote.api.TMDBRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow

class GenreStore (private val retrofitClient : TMDBRetrofitClient){
    val genres: MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())

    suspend fun fetchGenres() {
        genres.value = retrofitClient.api.fetchGenres().genres.map { genreDTO ->
            Genre(id = genreDTO.id, name = genreDTO.name)
        }
    }
}