package uk.co.bits.spacex.data.api

import retrofit2.Response
import retrofit2.http.GET
import uk.co.bits.spacex.data.response.LaunchResponse

interface LaunchesApiService {

    @GET("launches")
    suspend fun getLaunches(): Response<List<LaunchResponse>>
}
