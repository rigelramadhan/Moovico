package one.reevdev.moovico.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rivibi.mooviku.core.domain.model.Movie
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
        MovieHorizontalList(
            movies = popularMovies,
            onMoreClick = {},
            onItemClick = onClickMovie
        )
        MovieHorizontalList(
            movies = topRatedMovies,
            onMoreClick = {},
            onItemClick = onClickMovie
        )
    }
}