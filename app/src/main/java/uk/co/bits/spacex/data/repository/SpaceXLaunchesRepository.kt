package uk.co.bits.spacex.data.repository

import io.reactivex.Observable
import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.service.LaunchesService
import javax.inject.Inject

class SpaceXLaunchesRepository @Inject constructor(
    private val launchesService: LaunchesService,
    private val mapper: LaunchMapper
) : LaunchesRepository {

    private val launchList: MutableList<Launch> = mutableListOf()

    override fun getLaunches(): Observable<Result<List<Launch>>> {
        return loadLaunches().defaultIfEmpty(Result.success(launchList))
    }

    private fun loadLaunches(): Observable<Result<List<Launch>>> {
        return launchesService
            .getLaunches()
            .map(mapper::toLaunchList)
    }

    class UnableToLoadLaunchesError : Exception()
}
