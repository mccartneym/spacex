package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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
            val launchList = getLaunchesInteractor.getLaunches()
            Timber.e("*** no. of launches: ${launchList.size}")
            Timber.e("*** launches: $launchList")

            updateUi(launchList)
        }
    }

    fun updateUi(launchList: List<Launch>) {
        if (launchList.isNotEmpty()) {
            listViewState.value = LaunchListViewState.ListHasContent(launchList)
        } else {
            // TODO
            listViewState.value = LaunchListViewState.ListEmpty
        }
    }
}
