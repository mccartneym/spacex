package uk.co.bits.spacex.domain.model

data class Launch(
    val smallImageUrl: String?,
    val success: Boolean?,
    val name: String,
    val date: String
)
