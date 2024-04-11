package uk.co.bits.spacex.data.mapper

import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchMapper @Inject constructor(private val dateMapper: DateMapper) {

    fun toLaunch(launchResponse: LaunchResponse) = Launch(
        smallImageUrl = launchResponse.links.patch.small,
        success = launchResponse.wasSuccessful,
        name = launchResponse.name,
        date = dateMapper.parseUnixDate(launchResponse.dateUnix)
    )
}
