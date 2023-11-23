package one.reevdev.moovico.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rivibi.mooviku.core.domain.model.Movie
import one.reevdev.moovico.ui.component.ItemCategory

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    itemWidth: Dp = 180.dp,
    horizontalPadding: Dp = 16.dp,
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
                    .padding(
                        bottom = 4.dp,
                        start = horizontalPadding,
                        end = horizontalPadding
                    ),
                categoryName = it,
                onMoreClick = onMoreClick,
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            content = {
                items(movies) {
                    MovieItem(
                        title = it.title,
                        itemWidth = itemWidth,
                        caption = it.releaseDate,
                        imageUrl = it.posterPath,
                        onItemClick = { onItemClick(it.id) }
                    )
                }
            }
        )
    }
}