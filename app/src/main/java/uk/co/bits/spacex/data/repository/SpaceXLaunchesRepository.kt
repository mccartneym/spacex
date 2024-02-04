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

    override fun getLaunches(): Observable<List<Launch>> {
        return loadLaunches().defaultIfEmpty(launchList)
    }

    private fun loadLaunches(): Observable<List<Launch>> {
        return launchesService
            .getLaunches()
            .map {
                it.forEach { response -> launchList.add(mapper.toLaunch(response)) }
                launchList
            }
    }

    class UnableToLoadLaunchesError : Exception()
}
