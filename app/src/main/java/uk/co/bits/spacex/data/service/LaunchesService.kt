package uk.co.bits.spacex.data.service

import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchesService @Inject constructor(private val launchesApiService: LaunchesApiService) {

//    private val launchesApiService: LaunchesApiService
//        get() = apiProvider.getEmailReceiptApiService()

    suspend fun getLaunches(): List<LaunchResponse>? {
        val response = (launchesApiService as LaunchesApiService).getLaunches()

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
//        if (response.isSuccessful) {
//            return Result.Success(Unit)
//        } else {
//            return Result.Error("Failed to load launches")
//        }
    }
}
