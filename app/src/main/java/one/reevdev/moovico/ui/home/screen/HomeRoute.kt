package one.reevdev.moovico.ui.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import one.reevdev.moovico.utils.emptyString

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickMovie: (id: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }

    var searchValue by remember {
        mutableStateOf(emptyString())
    }

    LaunchedEffect(isRefreshing) {
        viewModel.getMovies()
        isRefreshing = false
    }

    HomeRoute(
        uiState = uiState,
        onClickMovie = onClickMovie,
        onSearchValueChange = {
            searchValue = it
        },
        onRefresh = { isRefreshing = true }
    )
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onClickMovie: (id: Int) -> Unit,
    onSearchValueChange: (input: String) -> Unit,
    onRefresh: () -> Unit, // TODO: For refresh implementation soon
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