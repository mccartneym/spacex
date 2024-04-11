package uk.co.bits.spacex.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uk.co.bits.spacex.R
import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.ui.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.LaunchListViewState.ListLoading
import uk.co.bits.spacex.ui.theme.SpaceXTheme

@Composable
fun LaunchListScreen(viewModel: LaunchListViewModel = hiltViewModel()) {
    val state: LaunchListViewState by viewModel.listViewState.collectAsState()
    LaunchList(state)
}

@Composable
fun LaunchList(state: LaunchListViewState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.launches_title),
            style = typography.headlineLarge,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(8.dp)
        )

        when (state) {
            ListLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            ListEmpty -> Text(
                text = stringResource(R.string.empty_list),
                style = typography.headlineLarge
            )

            ListError -> Text(
                text = stringResource(R.string.error),
                style = typography.headlineSmall
            )

            is ListHasContent -> LazyColumn {
                items(state.launchList) { item ->
                    LaunchListItem(item)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                }
            }
        }
    }
}

@Composable
fun LaunchListItem(launch: Launch) {
    Row {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(0.3f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(launch.smallImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = launch.name,
                style = typography.bodyMedium
            )
            Text(
                text = launch.date,
                style = typography.bodySmall,
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
