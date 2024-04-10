package uk.co.bits.spacex.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.domain.usecase.GetLaunchesUseCase
import uk.co.bits.spacex.ui.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.LaunchListViewState.ListLoading
import uk.co.bits.spacex.util.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunches: GetLaunchesUseCase,
    dispatcherProvider: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver {

    private val state = MutableStateFlow<LaunchListViewState>(ListLoading)
    val listViewState = state.asStateFlow()


    init {
        viewModelScope.launch(dispatcherProvider.default()) {
            state.emit(ListLoading)
            val launchListResult = getLaunches()
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
