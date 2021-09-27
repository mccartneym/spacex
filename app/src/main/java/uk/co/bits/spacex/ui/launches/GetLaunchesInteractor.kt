package uk.co.bits.spacex.ui.launches

import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.repository.LaunchesRepository
import javax.inject.Inject

class GetLaunchesInteractor @Inject constructor(private val launchesRepository: LaunchesRepository) {

    suspend fun getLaunches(): List<Launch> {
        return launchesRepository.getLaunches()
    }
}