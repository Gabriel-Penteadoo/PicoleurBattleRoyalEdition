package com.supdevinci.picoleurbattleroyaledition.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "drink_logs")
data class DrinkLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cocktailId: String,
    val cocktailName: String,
    val cocktailThumb: String?,
    val drunkAt: Date = Date()
)
