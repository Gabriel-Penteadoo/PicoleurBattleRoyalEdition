package com.supdevinci.picoleurbattleroyaledition.service

import com.supdevinci.picoleurbattleroyaledition.model.AlcoholicListResponse
import com.supdevinci.picoleurbattleroyaledition.model.CategoryListResponse
import com.supdevinci.picoleurbattleroyaledition.model.DrinkResponse
import com.supdevinci.picoleurbattleroyaledition.model.FilterResponse
import com.supdevinci.picoleurbattleroyaledition.model.GlassListResponse
import com.supdevinci.picoleurbattleroyaledition.model.IngredientListResponse
import com.supdevinci.picoleurbattleroyaledition.model.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // ── Search ────────────────────────────────────────────────────────────────

    /** Search cocktail by name — search.php?s=margarita */
    @GET("search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): DrinkResponse

    /** List all cocktails by first letter — search.php?f=a */
    @GET("search.php")
    suspend fun listCocktailsByFirstLetter(@Query("f") letter: String): DrinkResponse

    /** Search ingredient by name — search.php?i=vodka */
    @GET("search.php")
    suspend fun searchIngredientByName(@Query("i") ingredient: String): IngredientResponse

    // ── Lookup ────────────────────────────────────────────────────────────────

    /** Lookup full cocktail details by id — lookup.php?i=11007 */
    @GET("lookup.php")
    suspend fun lookupCocktailById(@Query("i") id: String): DrinkResponse

    /** Lookup ingredient by ID — lookup.php?iid=552 */
    @GET("lookup.php")
    suspend fun lookupIngredientById(@Query("iid") id: String): IngredientResponse

    // ── Random ────────────────────────────────────────────────────────────────

    /** Lookup a random cocktail — random.php */
    @GET("random.php")
    suspend fun getRandomCocktail(): DrinkResponse

    // ── Filter ────────────────────────────────────────────────────────────────

    /** Filter by ingredient — filter.php?i=Gin */
    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): FilterResponse

    /** Filter by alcoholic — filter.php?a=Alcoholic */
    @GET("filter.php")
    suspend fun filterByAlcoholic(@Query("a") alcoholic: String): FilterResponse

    /** Filter by category — filter.php?c=Ordinary_Drink */
    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): FilterResponse

    /** Filter by glass — filter.php?g=Cocktail_glass */
    @GET("filter.php")
    suspend fun filterByGlass(@Query("g") glass: String): FilterResponse

    // ── Lists ─────────────────────────────────────────────────────────────────

    /** List all categories — list.php?c=list */
    @GET("list.php")
    suspend fun listCategories(@Query("c") list: String = "list"): CategoryListResponse

    /** List all glasses — list.php?g=list */
    @GET("list.php")
    suspend fun listGlasses(@Query("g") list: String = "list"): GlassListResponse

    /** List all ingredients — list.php?i=list */
    @GET("list.php")
    suspend fun listIngredients(@Query("i") list: String = "list"): IngredientListResponse

    /** List alcoholic filter options — list.php?a=list */
    @GET("list.php")
    suspend fun listAlcoholicFilters(@Query("a") list: String = "list"): AlcoholicListResponse
}
