package one.reevdev.moovico.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.ui.component.ItemCategory

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    categoryName: String? = null,
    movies: List<Movie>,
    onMoreClick: (categoryName: String) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        categoryName?.let {
            ItemCategory(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                categoryName = it,
                onMoreClick = onMoreClick,
            )
        }
        LazyRow(
            content = {
                items(movies) {
                    MovieItem(
                        title = it.title,
                        caption = it.releaseDate,
                        imageUrl = it.posterPath,
                        onItemClick = { onItemClick(it.id) }
                    )
                }
            }
        )
    }
}