package uk.co.bits.spacex.data.mapper

import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.response.LaunchResponse
import javax.inject.Inject

class LaunchMapper @Inject constructor() {

    fun toLaunch(launchResponse: LaunchResponse): Launch {
        return Launch(
            smallImageUrl = launchResponse.links.patch.small.orEmpty(),
            success = launchResponse.success,
            rocketName = launchResponse.name,
            localDate = launchResponse.date_local
        )
    }
}
