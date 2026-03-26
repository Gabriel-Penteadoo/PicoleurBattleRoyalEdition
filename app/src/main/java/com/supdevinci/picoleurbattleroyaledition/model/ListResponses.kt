package com.supdevinci.picoleurbattleroyaledition.model

// list.php?c=list
data class CategoryItem(val strCategory: String)
data class CategoryListResponse(val drinks: List<CategoryItem>?)

// list.php?g=list
data class GlassItem(val strGlass: String)
data class GlassListResponse(val drinks: List<GlassItem>?)

// list.php?i=list
data class IngredientItem(val strIngredient1: String)
data class IngredientListResponse(val drinks: List<IngredientItem>?)

// list.php?a=list
data class AlcoholicItem(val strAlcoholic: String)
data class AlcoholicListResponse(val drinks: List<AlcoholicItem>?)
