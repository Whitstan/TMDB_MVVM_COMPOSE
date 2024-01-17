package com.mbh.moviebrowser

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbh.moviebrowser.features.movieDetails.MovieDetailsScreen
import com.mbh.moviebrowser.features.movieDetails.MovieDetailsViewModel
import com.mbh.moviebrowser.features.movieList.MovieListScreen
import com.mbh.moviebrowser.features.movieList.MovieListViewModel
import com.mbh.moviebrowser.features.movieList.MoviePagingSource
import com.mbh.moviebrowser.remote.api.TMDBRetrofitClient
import com.mbh.moviebrowser.store.genre.GenreStore
import com.mbh.moviebrowser.store.movie.MovieStore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitClient = TMDBRetrofitClient()
        val genreStore = GenreStore(retrofitClient)
        val movieStore = MovieStore()
        val pagingSource = MoviePagingSource(retrofitClient, genreStore, movieStore)
        val movieListViewModel = MovieListViewModel(movieStore, genreStore, pagingSource)
        val movieDetailsViewModel = MovieDetailsViewModel(movieStore)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        MovieListScreen(viewModel = movieListViewModel, navController)
                    }
                    composable("details") {
                        MovieDetailsScreen(
                            viewModel = movieDetailsViewModel,
                        )
                    }
                }
            }
        }
    }
}
