package uk.co.bits.spacex.data.mapper

import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchMapper @Inject constructor(private val dateMapper: DateMapper) {
    private val launchList = mutableListOf<Launch>()

    fun toLaunchList(responseList: Result<List<LaunchResponse>>): Result<List<Launch>> {
        return responseList.fold(
            onSuccess = {
                it.forEach { response -> launchList.add(toLaunch(response)) }
                Result.success(launchList)
            },
            onFailure = {
                Result.failure(LaunchListError())
            }
        )
    }

    fun toLaunch(launchResponse: LaunchResponse) = Launch(
        smallImageUrl = launchResponse.links.patch.small,
        success = launchResponse.success,
        name = launchResponse.name,
        date = dateMapper.parseUnixDate(launchResponse.date_unix)
    )

    class LaunchListError : IllegalStateException()
}
