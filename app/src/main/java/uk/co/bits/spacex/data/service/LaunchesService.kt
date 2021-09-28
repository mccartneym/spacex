package uk.co.bits.spacex.data.service

import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchesService @Inject constructor(private val launchesApiService: LaunchesApiService) {

    suspend fun getLaunches(): List<LaunchResponse>? {
        val response = launchesApiService.getLaunches()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
