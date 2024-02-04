package uk.co.bits.spacex.data.repository

import io.reactivex.Observable
import uk.co.bits.spacex.data.model.Launch

interface LaunchesRepository {
    fun getLaunches(): Observable<Result<List<Launch>>>
}
