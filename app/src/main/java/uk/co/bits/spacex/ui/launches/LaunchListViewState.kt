package uk.co.bits.spacex.ui.launches

import uk.co.bits.spacex.data.model.Launch

sealed class LaunchListViewState {
    data object ListLoading : LaunchListViewState()
    data object ListEmpty : LaunchListViewState()
    data object ListError : LaunchListViewState()
    data class ListHasContent(val launchList: List<Launch>) : LaunchListViewState()
}
