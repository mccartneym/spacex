package uk.co.bits.spacex.data.model

data class Launch(
    val smallImageUrl: String,
    val success: Boolean?,
    val rocketName: String,
    val localDate: String
)
