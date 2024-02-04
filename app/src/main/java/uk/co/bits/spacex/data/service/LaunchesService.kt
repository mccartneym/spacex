package uk.co.bits.spacex.data.service

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchesService @Inject constructor(private val launchesApiService: LaunchesApiService) {

    fun getLaunches(): Observable<List<LaunchResponse>?> {
        return launchesApiService
            .getLaunches()
            .subscribeOn(Schedulers.io())
    }

    class LaunchServiceApiError : Exception()
}
