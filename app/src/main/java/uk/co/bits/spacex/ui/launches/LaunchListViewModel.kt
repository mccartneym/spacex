package uk.co.bits.spacex.ui.launches

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListEmpty
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListError
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListHasContent
import uk.co.bits.spacex.ui.launches.LaunchListViewState.ListLoading
import uk.co.bits.spacex.util.SchedulerProvider
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesInteractor: GetLaunchesInteractor,
    private val schedulers: SchedulerProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val listViewState = MutableLiveData<LaunchListViewState>()

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onStart(owner: LifecycleOwner) {
        val disposable = getLaunchesInteractor
            .getLaunches()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnSubscribe { listViewState.postValue(ListLoading) }
            .subscribe(::updateUi, Timber::e)

        disposables.add(disposable)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        disposables.clear()
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
