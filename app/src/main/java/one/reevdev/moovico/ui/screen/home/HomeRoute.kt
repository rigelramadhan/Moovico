package one.reevdev.moovico.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickMovie: (id: Int) -> Unit,
    onSearchValueChange: (input: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeRoute(
        uiState = uiState,
        onClickMovie = onClickMovie,
        onSearchValueChange = onSearchValueChange
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onClickMovie: (id: Int) -> Unit,
    onSearchValueChange: (input: String) -> Unit,
) {
    when (uiState) {
        is HomeUiState.HasMovies -> {
            HomeScreen(
                onClickMovie = onClickMovie,
                onSearchValueChange = onSearchValueChange,
                popularMovies = uiState.homeFeed.popularMovies,
                topRatedMovies = uiState.homeFeed.topRatedMovies,
            )
        }

        is HomeUiState.NoMovies -> {
            // TODO: Make a screen for if there's no movie found
        }
    }
}