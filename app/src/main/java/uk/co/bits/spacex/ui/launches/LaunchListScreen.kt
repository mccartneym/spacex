package uk.co.bits.spacex.ui.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import timber.log.Timber
import uk.co.bits.spacex.R
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListLoading
import uk.co.bits.spacex.ui.theme.SpaceXTheme

@Composable
fun LaunchListScreen(viewModel: LaunchListViewModel = hiltViewModel()) {
    val state: LaunchListViewState by viewModel.listViewState.collectAsState()
    LaunchList(state)
}

@Composable
fun LaunchList(state: LaunchListViewState) {
    Timber.e("*** state: $state")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.launches_title),
            style = typography.headlineLarge
        )

        when (state) {
            ListLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                )
            }

            ListEmpty -> {
                Text(
                    text = stringResource(R.string.empty_list),
                    style = typography.headlineLarge
                )
            }

            ListError -> {
                Text(
                    text = stringResource(R.string.error),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            is ListHasContent -> {
                val list = state.launchList
                LazyColumn {
                    items(list.size) {
                        list.forEach {
                            LaunchListItem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LaunchListItem(launch: Launch) {
    Row {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(launch.smallImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null, // stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = launch.name,
                style = typography.headlineLarge
            )
            Text(
                text = launch.date,
                style = typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListHasContentPreview() {
    SpaceXTheme {
        LaunchList(
            ListHasContent(
                listOf(
                    Launch(
                        smallImageUrl = null,
                        success = true,
                        name = "Name1",
                        date = "Date1"
                    ),
                    Launch(
                        smallImageUrl = null,
                        success = true,
                        name = "Name2",
                        date = "Date2"
                    ),
                    Launch(
                        smallImageUrl = null,
                        success = true,
                        name = "Name3",
                        date = "Date3"
                    ),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListLoadingPreview() {
    SpaceXTheme {
        LaunchList(ListLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun ListEmptyPreview() {
    SpaceXTheme {
        LaunchList(ListEmpty)
    }
}

@Preview(showBackground = true)
@Composable
fun ListErrorPreview() {
    SpaceXTheme {
        LaunchList(ListError)
    }
}
