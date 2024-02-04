package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val listViewState = MutableLiveData<LaunchListViewState>()

    override fun onStart(owner: LifecycleOwner) {
        viewModelScope.launch(dispatcherProvider.default()) {
            listViewState.postValue(ListLoading)
            val launchListResult = getLaunchesInteractor.getLaunches()
            updateUi(launchListResult)
        }
    }

    private fun updateUi(result: Result<List<Launch>>) {
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
        listViewState.postValue(viewState)
    }
}
