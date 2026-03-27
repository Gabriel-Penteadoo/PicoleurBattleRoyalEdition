package com.supdevinci.picoleurbattleroyaledition.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE cocktailId = :cocktailId")
    suspend fun removeFavorite(cocktailId: String)

    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE cocktailId = :cocktailId)")
    fun isFavorite(cocktailId: String): Flow<Boolean>
}
