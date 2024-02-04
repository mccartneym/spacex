package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.ui.launches.LaunchListViewState.*
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
        val viewState = when {
            result.isSuccess -> {
                val list = result.getOrDefault(emptyList())
                if (list.isNotEmpty()) {
                    ListHasContent(list)
                } else {
                    ListEmpty
                }
            }
            else -> ListError
        }

        listViewState.postValue(viewState)
    }
}
