package com.supdevinci.picoleurbattleroyaledition.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.DrinkLogEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DrinkLogDao {

    @Insert
    suspend fun logDrink(log: DrinkLogEntity)

    @Query("SELECT * FROM drink_logs WHERE drunkAt >= :weekStart ORDER BY drunkAt DESC")
    fun getDrinksThisWeek(weekStart: Date): Flow<List<DrinkLogEntity>>

    @Query("SELECT * FROM drink_logs ORDER BY drunkAt DESC")
    fun getAllDrinkLogs(): Flow<List<DrinkLogEntity>>

    @Query("SELECT COUNT(*) FROM drink_logs WHERE cocktailId = :cocktailId")
    fun getDrinkCountForCocktail(cocktailId: String): Flow<Int>
}
