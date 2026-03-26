package com.supdevinci.picoleurbattleroyaledition.model

data class Ingredient(
    val idIngredient: String?,
    val strIngredient: String?,
    val strDescription: String?,
    val strType: String?,
    val strAlcohol: String?,
    val strABV: String?
)

data class IngredientResponse(
    val ingredients: List<Ingredient>?
)
