package com.mbh.moviebrowser.remote.dto.genre

import com.google.gson.annotations.SerializedName

data class GenresResponse (
    @SerializedName("genres") val genres: List<GenreDTO>
)