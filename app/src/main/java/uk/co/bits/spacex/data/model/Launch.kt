package uk.co.bits.spacex.data.model

data class Launch(
    val smallImageUrl: String,
    val largeImageUrl: String,
    val success: Boolean,
    val rocketName: String,
    val localDate: String
)
