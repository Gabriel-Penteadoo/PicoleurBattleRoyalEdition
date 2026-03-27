package com.supdevinci.picoleurbattleroyaledition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.picoleurbattleroyaledition.data.local.CocktailDatabase
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.DrinkLogEntity
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    private val db = CocktailDatabase.getDatabase(application)
    private val drinkLogDao = db.drinkLogDao()
    private val favoriteDao = db.favoriteDao()

    val drinksThisWeek: StateFlow<List<DrinkLogEntity>> =
        drinkLogDao.getDrinksThisWeek(getWeekStart())
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allDrinkLogs: StateFlow<List<DrinkLogEntity>> =
        drinkLogDao.getAllDrinkLogs()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favorites: StateFlow<List<FavoriteEntity>> =
        favoriteDao.getAllFavorites()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /** Favorites with their all-time drink count, combined reactively. */
    val favoritesWithCounts: StateFlow<List<Pair<FavoriteEntity, Int>>> =
        combine(favorites, allDrinkLogs) { favs, logs ->
            val countById = logs.groupBy { it.cocktailId }.mapValues { it.value.size }
            favs.map { fav -> fav to (countById[fav.cocktailId] ?: 0) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun logDrink(cocktailId: String, name: String, thumb: String?) {
        viewModelScope.launch {
            drinkLogDao.logDrink(DrinkLogEntity(cocktailId = cocktailId, cocktailName = name, cocktailThumb = thumb))
        }
    }

    fun toggleFavorite(cocktailId: String, name: String, thumb: String?) {
        viewModelScope.launch {
            if (favorites.value.any { it.cocktailId == cocktailId }) {
                favoriteDao.removeFavorite(cocktailId)
            } else {
                favoriteDao.addFavorite(FavoriteEntity(cocktailId = cocktailId, name = name, thumb = thumb))
            }
        }
    }

    fun isFavorite(cocktailId: String): Flow<Boolean> = favoriteDao.isFavorite(cocktailId)

    fun getDrinkCount(cocktailId: String): Flow<Int> = drinkLogDao.getDrinkCountForCocktail(cocktailId)

    /** Nombre maximum de cocktails bus sur une seule semaine (record personnel). */
    fun calculatePersonalBest(logs: List<DrinkLogEntity>): Int {
        if (logs.isEmpty()) return 0
        val calendar = Calendar.getInstance()
        return logs
            .groupBy { log ->
                calendar.time = log.drunkAt
                "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.WEEK_OF_YEAR)}"
            }
            .values
            .maxOf { it.size }
    }

    /** Cocktail le plus bu all-time (nom). */
    fun getMostDrunkCocktail(logs: List<DrinkLogEntity>): String? {
        return logs
            .groupBy { it.cocktailId }
            .maxByOrNull { it.value.size }
            ?.value
            ?.firstOrNull()
            ?.cocktailName
    }

    private fun getWeekStart(): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}
