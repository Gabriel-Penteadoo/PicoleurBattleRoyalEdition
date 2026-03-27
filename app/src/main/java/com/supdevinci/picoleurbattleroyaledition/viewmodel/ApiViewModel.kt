package com.supdevinci.picoleurbattleroyaledition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.picoleurbattleroyaledition.data.remote.RetrofitInstance
import com.supdevinci.picoleurbattleroyaledition.model.Drink
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ApiViewModel : ViewModel() {

    private val api = RetrofitInstance.api

    private val _searchResults = MutableStateFlow<List<Drink>>(emptyList())
    val searchResults: StateFlow<List<Drink>> = _searchResults.asStateFlow()

    private val _trendingCocktails = MutableStateFlow<List<Drink>>(emptyList())
    val trendingCocktails: StateFlow<List<Drink>> = _trendingCocktails.asStateFlow()

    private val _detailCocktail = MutableStateFlow<Drink?>(null)
    val detailCocktail: StateFlow<Drink?> = _detailCocktail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun searchCocktails(query: String) {
        if (query.isBlank()) { _searchResults.value = emptyList(); return }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _searchResults.value = api.searchCocktailByName(query).drinks ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de recherche : ${e.message}"
                _searchResults.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun loadTrending() {
        if (_trendingCocktails.value.isNotEmpty()) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = supervisorScope {
                    (1..10).map {
                        async {
                            try { api.getRandomCocktail().drinks?.firstOrNull() }
                            catch (e: Exception) { null }
                        }
                    }.awaitAll()
                }.filterNotNull().distinctBy { it.idDrink }
                _trendingCocktails.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de chargement : ${e.message}"
            }
            _isLoading.value = false
        }
    }

    fun loadCocktailDetail(id: String) {
        _detailCocktail.value = null
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _detailCocktail.value = api.lookupCocktailById(id).drinks?.firstOrNull()
            } catch (e: Exception) {
                _errorMessage.value = "Impossible de charger le cocktail."
            }
            _isLoading.value = false
        }
    }

    fun clearError() { _errorMessage.value = null }
}
