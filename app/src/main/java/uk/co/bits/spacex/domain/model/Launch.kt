package uk.co.bits.spacex.domain.model

data class Launch(
    val smallImageUrl: String?,
    val wasSuccessful: Boolean?,
    val name: String,
    val date: String
)
