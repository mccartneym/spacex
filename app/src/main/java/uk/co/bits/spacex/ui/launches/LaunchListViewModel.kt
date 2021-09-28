package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.bits.spacex.data.model.Launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesInteractor: GetLaunchesInteractor
) : ViewModel(), LifecycleObserver {

    val listViewState = MutableLiveData<LaunchListViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        viewModelScope.launch {
            listViewState.value = LaunchListViewState.ListLoading
            val launchListResult = getLaunchesInteractor.getLaunches()
            updateUi(launchListResult)
        }
    }

    private fun updateUi(result: Result<List<Launch>>) {
        when {
            result.isSuccess -> {
                val list = result.getOrDefault(emptyList())
                if (list.isNotEmpty()) {
                    listViewState.value = LaunchListViewState.ListHasContent(list)
                } else {
                    listViewState.value = LaunchListViewState.ListEmpty
                }
            }
            result.isFailure -> {
                listViewState.value = LaunchListViewState.ListError
            }
        }
    }
}
