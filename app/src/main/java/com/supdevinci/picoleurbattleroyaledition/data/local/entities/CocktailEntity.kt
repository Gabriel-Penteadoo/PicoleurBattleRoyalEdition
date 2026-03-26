package com.supdevinci.picoleurbattleroyaledition.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val instructions: String,
    val isFavorite: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date? = null,
    val deletedAt: Date? = null
)
