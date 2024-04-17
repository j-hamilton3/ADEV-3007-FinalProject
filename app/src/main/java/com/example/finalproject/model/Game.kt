package com.example.finalproject.model
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Entity(
    tableName = "games",
)
@Serializable
data class Game(
    @PrimaryKey(autoGenerate = true)
    var favId: Int = 0, // Auto Inc PK, to allow multiple users to favorite the same game.
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
    var freetogameProfileUrl: String,
    var userId: String? = null
)