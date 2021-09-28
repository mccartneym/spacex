package uk.co.bits.spacex.ui.launches

import uk.co.bits.spacex.data.model.Launch

sealed class LaunchListViewState {
    object ListLoading : LaunchListViewState()
    object ListEmpty : LaunchListViewState()
    object ListError : LaunchListViewState()
    data class ListHasContent(val launchList: List<Launch>) : LaunchListViewState()
}
