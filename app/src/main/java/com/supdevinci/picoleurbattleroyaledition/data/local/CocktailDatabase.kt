package com.supdevinci.picoleurbattleroyaledition.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.supdevinci.picoleurbattleroyaledition.data.local.dao.CocktailDao
import com.supdevinci.picoleurbattleroyaledition.data.local.dao.DrinkLogDao
import com.supdevinci.picoleurbattleroyaledition.data.local.dao.FavoriteDao
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.CocktailEntity
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.DrinkLogEntity
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.FavoriteEntity

@Database(
    entities = [CocktailEntity::class, DrinkLogEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
    abstract fun drinkLogDao(): DrinkLogDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun getDatabase(context: Context): CocktailDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CocktailDatabase::class.java,
                    "cocktail_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
