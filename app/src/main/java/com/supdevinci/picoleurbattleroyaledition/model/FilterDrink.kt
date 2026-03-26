package com.supdevinci.picoleurbattleroyaledition.model

// Returned by filter endpoints — only contains partial drink info
data class FilterDrink(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String?
)

data class FilterResponse(
    val drinks: List<FilterDrink>?
)
