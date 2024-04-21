package com.example.finalproject.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SystemRequirements(
    var os: String,
    var processor: String,
    var memory: String,
    var graphics: String,
    var storage: String
)

@Serializable
data class Screenshot(
    var id: Int,
    var image: String
)

@Serializable
data class GameDetails(
    var id: Int,
    var title: String,
    var thumbnail: String,
    var status: String,
    @SerialName("short_description") var shortDescription: String,
    var description: String,
    @SerialName("game_url") var gameUrl: String,
    var genre: String,
    var platform: String,
    var publisher: String,
    var developer: String,
    @SerialName("release_date") var releaseDate: String,
    @SerialName("freetogame_profile_url") var freetogameProfileUrl: String,
    @SerialName("minimum_system_requirements") var minimumSystemRequirements: SystemRequirements,
    var screenshots: List<Screenshot>
)