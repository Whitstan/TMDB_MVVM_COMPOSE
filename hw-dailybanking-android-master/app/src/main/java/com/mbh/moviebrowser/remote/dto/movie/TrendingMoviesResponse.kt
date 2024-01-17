package com.mbh.moviebrowser.remote.dto.movie

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse (
    @SerializedName("results") val results: List<MovieDTO>
)