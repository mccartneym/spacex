package uk.co.bits.spacex.ui.launches

import io.reactivex.Observable
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.repository.LaunchesRepository
import javax.inject.Inject

class GetLaunchesInteractor @Inject constructor(private val launchesRepository: LaunchesRepository) {

    fun getLaunches(): Observable<List<Launch>> = launchesRepository.getLaunches()
}
