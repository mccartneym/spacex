package uk.co.bits.spacex.data.repository

import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.data.api.LaunchesService
import uk.co.bits.spacex.domain.repository.LaunchesRepository
import javax.inject.Inject

class SpaceXLaunchesRepository @Inject constructor(
    private val launchesService: LaunchesService,
    private val mapper: LaunchMapper
) : LaunchesRepository {

    private val launchList: MutableList<Launch> = mutableListOf()

    override suspend fun getLaunches(): Result<List<Launch>> {
        val result = if (launchList.isEmpty()) {
            loadLaunches()
        } else {
            Result.success(launchList)
        }

        return result
    }

    private suspend fun loadLaunches(): Result<List<Launch>> {
        return launchesService.getLaunches().fold(
            onSuccess = {
                it?.forEach { response -> launchList.add(mapper.toLaunch(response)) }
                Result.success(launchList)
            },
            onFailure = {
                Result.failure(UnableToLoadLaunchesError())
            }
        )
    }

    class UnableToLoadLaunchesError : Exception()
}
