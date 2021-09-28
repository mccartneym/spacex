package uk.co.bits.spacex.data.repository

import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.service.LaunchesService
import javax.inject.Inject

class LaunchesRepository @Inject constructor(
    private val launchesService: LaunchesService,
    private val mapper: LaunchMapper
) {

    private val launchList: MutableList<Launch> = mutableListOf()

    suspend fun getLaunches(): Result<List<Launch>> {
        val result = if (launchList.isEmpty()) {
            loadLaunches()
        } else {
            Result.success(launchList)
        }

        return result
    }

    private suspend fun loadLaunches(): Result<List<Launch>> {
        val launchesResponse = launchesService.getLaunches()

        return if (launchesResponse.isSuccess) {
            launchesResponse.getOrDefault(emptyList())?.forEach { response ->
                launchList.add(mapper.toLaunch(response))
            }
            Result.success(launchList)
        } else {
            Result.failure(UnableToLoadLaunchesError())
        }
    }

    class UnableToLoadLaunchesError : Exception()
}
