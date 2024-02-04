package uk.co.bits.spacex.ui.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListLoading
import uk.co.bits.spacex.ui.theme.SpaceXTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun LaunchListScreen() {
    val viewModel: LaunchListViewModel = viewModel()

    val state: LaunchListViewState by viewModel.listViewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Launches",
            style = MaterialTheme.typography.headlineLarge
        )

        when (state) {
            ListLoading -> {
                CircularProgressIndicator(modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp))
            }
            ListEmpty -> {
                Text(
                    text = "Empty List",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            ListError -> {
                Text(
                    text = "Error",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            is ListHasContent -> {
                LazyColumn {
                    items(state.launchList) { launch ->
                        LaunchListItem(launch = launch)
                    }
                }
            }
        }
    }
}

@Composable
fun LaunchListItem(launch: Launch) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = launch.name,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = launch.date,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchListScreenPreview() {
    SpaceXTheme {
        LaunchListScreen()
    }
}
