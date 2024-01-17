package com.mbh.moviebrowser.features.movieList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.mbh.moviebrowser.domain.movie.Movie

const val COVER_URL_PREFIX = "https://image.tmdb.org/t/p/w500"

@Composable
fun MovieListScreen(viewModel: MovieListViewModel, navController: NavController) {
    MovieListScreenUI(viewModel, navController)
}

@Composable
fun MovieListScreenUI(viewModel: MovieListViewModel, navController: NavController) {
    val pagingItems: LazyPagingItems<Movie> = viewModel.movies.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(pagingItems.itemCount) { index ->
            pagingItems[index]?.let { movie ->
                MovieListItem(
                    movie = movie,
                    onDetailsClicked = {
                        viewModel.updateSelectedMovie(it)
                        navController.navigate("details")
                    },
                )
            }
        }
    }
}

@Composable
private fun MovieListItem(
    movie: Movie,
    onDetailsClicked: (Movie) -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onDetailsClicked(movie)
            },
    ) {
        Box {
            AsyncImage(
                model = COVER_URL_PREFIX + movie.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(80.dp)
                    .zIndex(1.0f),
            )

            Image(
                painter = if (movie.isFavorite) painterResource(id = android.R.drawable.btn_star_big_on) else painterResource(id = android.R.drawable.btn_star_big_off),
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 4.dp)
                    .zIndex(2.0f)
                    .align(Alignment.TopEnd),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.genres,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = movie.rating / 10.0f, modifier = Modifier.fillMaxWidth())
            Text(
                text = movie.rating.toString(),
                Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
            )
        }
    }
}
