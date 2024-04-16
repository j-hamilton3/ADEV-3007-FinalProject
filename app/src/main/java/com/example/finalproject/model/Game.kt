package com.example.finalproject.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Entity(tableName = "games")
@Serializable
data class Game(
    @PrimaryKey
    var id: Int,
    var title: String,
    var thumbnail: String,
    @SerialName("short_description")
    var shortDescription: String,
    @SerialName("game_url")
    var gameUrl: String,
    var genre: String,
    var platform: String,
    var publisher: String,
    var developer: String,
    @SerialName("release_date")
    var releaseDate: String,
    @SerialName("freetogame_profile_url")
    var freetogameProfileUrl: String
)