package uk.co.bits.spacex.data.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import uk.co.bits.spacex.data.response.LaunchResponse

interface LaunchesApiService {

    @GET("launches")
    fun getLaunches(): Observable<Response<List<LaunchResponse>>>
}
