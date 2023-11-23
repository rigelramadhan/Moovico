package one.reevdev.moovico.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.R
import one.reevdev.moovico.ui.component.MovieHorizontalList
import one.reevdev.moovico.ui.theme.MoovicoTheme
import one.reevdev.moovico.ui.home.component.MovieHorizontalList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickMovie: (id: Int) -> Unit,
    onSearchValueChange: (input: String) -> Unit,
    popularMovies: List<Movie> = emptyList(),
    topRatedMovies: List<Movie> = emptyList(),
) {
    Column(modifier = modifier) {
        // TODO: Add search component
        if (popularMovies.isNotEmpty())
            MovieHorizontalList(
                modifier = Modifier,
                movies = popularMovies,
                itemWidth = 140.dp,
                categoryName = stringResource(id = R.string.label_popular),
                onMoreClick = {},
                onItemClick = onClickMovie
            )
        Spacer(modifier = Modifier.height(24.dp))
        if (topRatedMovies.isNotEmpty())
            MovieHorizontalList(
                modifier = Modifier,
                movies = topRatedMovies,
                itemWidth = 140.dp,
                categoryName = stringResource(id = R.string.label_top_rated),
                onMoreClick = {},
                onItemClick = onClickMovie
            )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MoovicoTheme {
        val movieDummy = Movie(
            title = "This is the Title 2019: The Beginning of the End",
            id = 0,
            releaseDate = "17 November 2019"
        )
        val movieDummyList = listOf(
            movieDummy,
            movieDummy,
            movieDummy,
            movieDummy,
            movieDummy,
            movieDummy,
            movieDummy
        )
        HomeScreen(
            onClickMovie = {},
            onSearchValueChange = {},
            popularMovies = movieDummyList,
            topRatedMovies = movieDummyList
        )
    }
}