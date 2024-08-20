package uk.co.bits.spacex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import timber.log.Timber
import uk.co.bits.spacex.R
import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.ui.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.LaunchListViewState.ListLoading
import uk.co.bits.spacex.ui.theme.SpaceXTheme

@Composable
fun LaunchListScreen(
    viewModel: LaunchListViewModel = hiltViewModel(),
    onViewLaunchClicked: (String?) -> Unit
) {
    val viewState: LaunchListViewState by viewModel.listViewState.collectAsState()
    val scrollState: LazyListState = rememberLazyListState()
    LaunchList(viewState, scrollState, onViewLaunchClicked)
}

@Composable
private fun LaunchList(
    state: LaunchListViewState,
    scrollState: LazyListState,
    onViewLaunchClicked: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (state) {
            ListLoading -> {
                Title()
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                            .align(alignment = Alignment.Center)
                    )
                }
            }

            ListEmpty -> {
                Title()
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        text = stringResource(R.string.empty_list),
                        style = typography.headlineLarge,
                    )
                }
            }

            ListError -> {
                Title()
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        text = stringResource(R.string.error),
                        style = typography.headlineSmall
                    )
                }
            }

            is ListHasContent -> {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = stringResource(R.string.launches_title),
                        style = typography.headlineLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    ScrollToBottomButton(scrollState, state.launchList.size)
                }
                LazyColumn(state = scrollState) {
                    items(state.launchList) { item ->
                        LaunchListItem(item, onViewLaunchClicked)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.Title() {
    Text(
        text = stringResource(R.string.launches_title),
        style = typography.headlineLarge,
        modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .padding(8.dp)
    )
}

@Composable
fun ScrollToBottomButton(scrollState: LazyListState, listLength: Int) {
    Timber.e("ScrollToBottomButton")
    val scope = rememberCoroutineScope()
    var isAtTop by remember { mutableStateOf(true) }
    val icon = if (isAtTop) {
        Icons.Filled.KeyboardArrowDown
    } else {
        Icons.Filled.KeyboardArrowUp
    }

    FloatingActionButton(onClick = {
        scope.launch {
            val destination = if (isAtTop) {
                listLength
            } else {
                0
            }
            scrollState.scrollToItem(destination)
            isAtTop = !isAtTop
        }
    }) {
        Icon(icon, "Small floating action button.")
    }
}

@Composable
fun LaunchListItem(
    launch: Launch,
    onViewLaunchClicked: (String?) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (image, topText, middleText, bottomText, outcome, footage) = createRefs()
        createVerticalChain(topText, middleText, bottomText, footage)

        AsyncImage(
            modifier = Modifier
                .constrainAs(image) {}
                .fillMaxWidth(0.3f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(launch.smallImageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(topText) {
                    start.linkTo(image.end)
                },
            text = launch.name,
            fontWeight = FontWeight.Bold,
            style = typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(middleText) {
                    start.linkTo(image.end)
                },
            text = stringResource(id = R.string.launch_date, launch.date),
            style = typography.bodyMedium,
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(bottomText) {
                    start.linkTo(image.end)
                },
            text = stringResource(id = R.string.mission_success),
            style = typography.bodyMedium,
        )

        Button(
            onClick = {
                onViewLaunchClicked(launch.videoId)
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(footage) {
                    start.linkTo(image.end)
                },
            enabled = launch.videoId != null
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.launch_footage),
                style = typography.bodyMedium,
            )
        }

        val outComeDrawable = when (launch.wasSuccessful) {
            true -> R.drawable.ic_baseline_check_24
            false -> R.drawable.ic_baseline_clear_24
            else -> R.drawable.ic_baseline_help_24
        }

        Image(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(outcome) {
                    start.linkTo(bottomText.end)
                    centerVerticallyTo(bottomText)
                },
            painter = painterResource(id = outComeDrawable),
            contentDescription = null
        )
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
                        wasSuccessful = true,
                        name = "Name1",
                        date = "Date1",
                        videoId = null
                    ),
                    Launch(
                        smallImageUrl = null,
                        wasSuccessful = true,
                        name = "Name2",
                        date = "Date2",
                        videoId = null
                    ),
                    Launch(
                        smallImageUrl = null,
                        wasSuccessful = true,
                        name = "Name3",
                        date = "Date3",
                        videoId = null
                    ),
                )
            ),
            LazyListState()
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ListLoadingPreview() {
    SpaceXTheme {
        LaunchList(ListLoading, LazyListState()) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ListEmptyPreview() {
    SpaceXTheme {
        LaunchList(ListLoading, LazyListState()) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ListErrorPreview() {
    SpaceXTheme {
        LaunchList(ListLoading, LazyListState()) {}
    }
}
