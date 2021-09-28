package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.util.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesInteractor: GetLaunchesInteractor,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), LifecycleObserver {

    val listViewState = MutableLiveData<LaunchListViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        viewModelScope.launch(dispatcherProvider.default()) {
            listViewState.postValue(LaunchListViewState.ListLoading)
            val launchListResult = getLaunchesInteractor.getLaunches()
            updateUi(launchListResult)
        }
    }

    private fun updateUi(result: Result<List<Launch>>) {
        when {
            result.isSuccess -> {
                val list = result.getOrDefault(emptyList())
                if (list.isNotEmpty()) {
                    listViewState.postValue(LaunchListViewState.ListHasContent(list))
                } else {
                    listViewState.postValue(LaunchListViewState.ListEmpty)
                }
            }
            result.isFailure -> {
                listViewState.postValue(LaunchListViewState.ListError)
            }
        }
    }
}
