package one.reevdev.moovico.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import one.reevdev.moovico.R
import one.reevdev.moovico.core.data.Resource
import one.reevdev.moovico.utils.ErrorMessage
import one.reevdev.moovico.utils.emptyString
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
        )
    )

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val popularMoviesFlow = movieUseCase.getPopular()
            val topRatedMoviesFlow = movieUseCase.getTopRated()

            combine(popularMoviesFlow, topRatedMoviesFlow) { popular, topRated ->
                viewModelState.update {
                    if (popular is Resource.Error && topRated is Resource.Error)
                        it.copy(isLoading = false, errorMessage = ErrorMessage())
                    else
                        it.copy(
                            isLoading = false,
                            homeFeed = HomeFeed(
                                popularMovies = popular.data.orEmpty(),
                                topRatedMovies = topRated.data.orEmpty(),
                                upcomingMovies = emptyList()
                            )
                        )
                }
            }
        }
    }
}

private data class HomeViewModelState(
    val homeFeed: HomeFeed? = null,
    val isLoading: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val search: String = emptyString()
) {

    fun toUiState(): HomeUiState = if (homeFeed != null) {
        HomeUiState.HasMovies(
            homeFeed,
            isLoading,
            errorMessage ?: ErrorMessage(0),
            search,
        )
    } else {
        HomeUiState.NoMovies(
            isLoading,
            errorMessage ?: ErrorMessage(R.string.message_generic_error),
            search,
        )
    }

}

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessage: ErrorMessage
    val search: String

    data class NoMovies(
        override val isLoading: Boolean,
        override val errorMessage: ErrorMessage,
        override val search: String
    ) : HomeUiState

    data class HasMovies(
        val homeFeed: HomeFeed,
        override val isLoading: Boolean,
        override val errorMessage: ErrorMessage,
        override val search: String
    ) : HomeUiState
}

data class HomeFeed(
    val popularMovies: List<Movie>,
    val topRatedMovies: List<Movie>,
    val upcomingMovies: List<Movie>,
)