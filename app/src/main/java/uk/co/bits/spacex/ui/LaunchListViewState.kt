package uk.co.bits.spacex.ui

import uk.co.bits.spacex.domain.model.Launch

sealed class LaunchListViewState {
    data object ListLoading : LaunchListViewState()
    data object ListEmpty : LaunchListViewState()
    data object ListError : LaunchListViewState()
    data class ListHasContent(val launchList: List<Launch>) : LaunchListViewState()
}
