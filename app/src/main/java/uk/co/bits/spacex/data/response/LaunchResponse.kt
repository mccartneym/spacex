package uk.co.bits.spacex.data.response

data class LaunchResponse constructor(
    val links: Links,
    val success: Boolean?,
    val name: String,
    val date_local: String
)

data class Links constructor(val patch: Patch)

data class Patch constructor(val small: String?)
