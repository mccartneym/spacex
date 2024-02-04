package uk.co.bits.spacex.data.mapper

import retrofit2.Response
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchResponseMapper @Inject constructor() {

    fun toLaunchResult(launchResponse: Response<List<LaunchResponse>>): Result<List<LaunchResponse>> {
        val body = launchResponse.body()
        return if (launchResponse.isSuccessful && body != null) {
            Result.success(body)
        } else {
            Result.failure(LaunchServiceApiError(launchResponse.errorBody().toString()))
        }
    }

    class LaunchServiceApiError(message: String) : IllegalStateException(message)
}
