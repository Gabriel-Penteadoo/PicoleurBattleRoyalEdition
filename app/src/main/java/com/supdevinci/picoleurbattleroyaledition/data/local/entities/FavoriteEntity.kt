package com.supdevinci.picoleurbattleroyaledition.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val cocktailId: String,
    val name: String,
    val thumb: String?,
    val addedAt: Date = Date()
)
