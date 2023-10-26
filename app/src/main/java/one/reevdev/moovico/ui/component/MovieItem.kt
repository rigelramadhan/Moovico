package one.reevdev.moovico.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import one.reevdev.moovico.R
import one.reevdev.moovico.ui.theme.MoovicoTheme
import one.reevdev.moovico.ui.theme.TransparentGray
import one.reevdev.moovico.utils.emptyString

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    title: String = emptyString(),
    caption: String = emptyString(),
    onItemClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(TransparentGray)
                .aspectRatio(0.8f),
            model = imageUrl,
            contentDescription = stringResource(R.string.description_movie_image),
        )
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            MoovicoText(
                modifier = Modifier,
                style = MaterialTheme.typography.bodyMedium
                    .copy(fontWeight = FontWeight.Medium),
                text = title,
                maxLines = 2,
            )
            MoovicoText(
                modifier = Modifier
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.labelMedium
                    .copy(fontWeight = FontWeight.Light),
                text = caption,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MoovicoTheme {
        MovieItem(
            modifier = Modifier
                .width(240.dp),
            title = "This is the Title 2019",
            caption = "27 May 2021"
        ) {}
    }
}