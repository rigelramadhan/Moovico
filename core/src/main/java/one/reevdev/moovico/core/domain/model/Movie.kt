package com.rivibi.mooviku.core.domain.model

import one.reevdev.moovico.core.utils.emptyString

data class Movie(
    val overview: String = emptyString(),
    val originalLanguage: String = emptyString(),
    val originalTitle: String = emptyString(),
    val video: Boolean = false,
    val title: String,
    val genreIds: List<Int> = emptyList(),
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String,
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val id: Int,
    val adult: Boolean = false,
    val voteCount: Int = 0,
    val category: String = emptyString(),
    val favorite: Boolean = false,
)
