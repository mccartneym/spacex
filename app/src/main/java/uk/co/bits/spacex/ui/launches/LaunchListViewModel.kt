package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListLoading
import uk.co.bits.spacex.util.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesInteractor: GetLaunchesInteractor,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver {

    private val state = MutableStateFlow<LaunchListViewState>(ListEmpty)
    val listViewState = state.asStateFlow()


    override fun onStart(owner: LifecycleOwner) {
        viewModelScope.launch(dispatcherProvider.default()) {
            state.emit(ListLoading)
            val launchListResult = getLaunchesInteractor.getLaunches()
            updateUi(launchListResult)
        }
    }

    private suspend fun updateUi(result: Result<List<Launch>>) {
        val viewState = result.fold(
            onSuccess = { list ->
                if (list.isNotEmpty()) {
                    ListHasContent(list)
                } else {
                    ListEmpty
                }
            },
            onFailure = { ListError },
        )
        state.emit(viewState)
    }
}
