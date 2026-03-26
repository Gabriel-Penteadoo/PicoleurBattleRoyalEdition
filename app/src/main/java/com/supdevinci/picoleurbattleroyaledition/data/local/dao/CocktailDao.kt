package com.supdevinci.picoleurbattleroyaledition.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.CocktailEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Query("UPDATE cocktails SET deletedAt = :date WHERE id = :id")
    suspend fun softDelete(id: Int, date: Date)

    @Query("SELECT * FROM cocktails WHERE deletedAt IS NULL ORDER BY createdAt DESC")
    fun getAllVisibleCocktails(): Flow<List<CocktailEntity>>
}
