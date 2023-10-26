package one.reevdev.moovico.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import one.reevdev.moovico.R
import one.reevdev.moovico.ui.theme.MoovicoTheme

@Composable
fun ItemCategory(
    modifier: Modifier = Modifier,
    categoryName: String,
    onMoreClick: (categoryName: String) -> Unit,
    horizontalPadding: Dp = 0.dp,
) {
    Row(
        modifier = modifier
            .padding(horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoovicoText(
            modifier = Modifier
                .weight(1f),
            text = categoryName,
        )
        IconButton(
            modifier = Modifier,
            onClick = { onMoreClick(categoryName) }) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = stringResource(R.string.description_more_item)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCategoryPreview() {
    MoovicoTheme {
        ItemCategory(categoryName = "Popular Right Now!", onMoreClick = {})
    }
}