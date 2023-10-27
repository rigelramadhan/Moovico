package one.reevdev.moovico.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.ui.component.MovieHorizontalList

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