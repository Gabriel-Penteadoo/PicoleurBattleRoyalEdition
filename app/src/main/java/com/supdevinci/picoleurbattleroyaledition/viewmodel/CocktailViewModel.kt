package com.supdevinci.picoleurbattleroyaledition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.picoleurbattleroyaledition.data.local.CocktailDatabase
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.CocktailEntity
import com.supdevinci.picoleurbattleroyaledition.utils.CocktailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CocktailDatabase.getDatabase(application).cocktailDao()

    private val _state = MutableStateFlow<CocktailState>(CocktailState.Loading)
    val state: StateFlow<CocktailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllVisibleCocktails().collect { cocktails ->
                _state.value = if (cocktails.isEmpty()) CocktailState.Empty
                               else CocktailState.Success(cocktails)
            }
        }
    }

    fun addCocktail(name: String, instructions: String) {
        viewModelScope.launch {
            dao.insert(CocktailEntity(name = name, instructions = instructions))
        }
    }

    fun toggleFavorite(cocktail: CocktailEntity) {
        viewModelScope.launch {
            dao.update(cocktail.copy(isFavorite = !cocktail.isFavorite, updatedAt = Date()))
        }
    }

    fun archiveCocktail(id: Int) {
        viewModelScope.launch {
            dao.softDelete(id, Date())
        }
    }
}
