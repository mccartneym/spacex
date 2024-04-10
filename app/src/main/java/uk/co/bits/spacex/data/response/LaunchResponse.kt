package uk.co.bits.spacex.data.response

import com.squareup.moshi.Json

data class LaunchResponse(
    @Json(name = "links")
    val links: Links,
    @Json(name = "success")
    val success: Boolean?,
    @Json(name = "name")
    val name: String,
    @Json(name = "date_unix")
    val dateUnix: Long
)

data class Links(val patch: Patch)

data class Patch(val small: String?)
