package uk.co.bits.spacex.data.repository

import timber.log.Timber
import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.service.LaunchesService
import javax.inject.Inject

class LaunchesRepository @Inject constructor(
    private val launchesService: LaunchesService,
    private val mapper: LaunchMapper
) {

    private val launchList: MutableList<Launch> = mutableListOf()

    suspend fun getLaunches(): List<Launch> {
        Timber.e("*** getLaunches")
        if (launchList.isEmpty()) {
            Timber.e("*** getLaunches.loadingLaunches")
            loadLaunches()
        }

        return launchList
    }

    private suspend fun loadLaunches() {
        val launchesResponse = launchesService.getLaunches()
        launchesResponse?.forEach { response ->
            launchList.add(mapper.toLaunch(response))
        }
    }
}
