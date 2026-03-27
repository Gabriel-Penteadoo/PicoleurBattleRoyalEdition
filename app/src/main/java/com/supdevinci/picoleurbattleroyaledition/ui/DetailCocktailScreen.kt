package com.supdevinci.picoleurbattleroyaledition.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.supdevinci.picoleurbattleroyaledition.viewmodel.ApiViewModel
import com.supdevinci.picoleurbattleroyaledition.viewmodel.DrinkViewModel

@Composable
fun DetailCocktailScreen(
    cocktailId: String,
    onBack: () -> Unit,
    apiViewModel: ApiViewModel = viewModel(),
    drinkViewModel: DrinkViewModel = viewModel()
) {
    val cocktail by apiViewModel.detailCocktail.collectAsStateWithLifecycle()
    val isLoading by apiViewModel.isLoading.collectAsStateWithLifecycle()
    val isFavorite by drinkViewModel.isFavorite(cocktailId).collectAsStateWithLifecycle(false)
    val drinkCount by drinkViewModel.getDrinkCount(cocktailId).collectAsStateWithLifecycle(0)

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(cocktailId) { apiViewModel.loadCocktailDetail(cocktailId) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading && cocktail == null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            cocktail == null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("Impossible de charger le cocktail.") }

            else -> {
                val drink = cocktail!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header avec bouton retour et favori
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                        }
                        IconButton(onClick = {
                            drinkViewModel.toggleFavorite(drink.idDrink, drink.strDrink, drink.strDrinkThumb)
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favori",
                                tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Image
                    AsyncImage(
                        model = drink.strDrinkThumb,
                        contentDescription = drink.strDrink,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        // Nom
                        Text(
                            text = drink.strDrink,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        // Métadonnées
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            drink.strCategory?.let {
                                Chip(text = it)
                            }
                            drink.strAlcoholic?.let {
                                Chip(text = it)
                            }
                            drink.strGlass?.let {
                                Chip(text = it)
                            }
                        }

                        // Compteur de verres bus
                        if (drinkCount > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Bu $drinkCount fois",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))

                        // Ingrédients
                        Text(
                            text = "Ingrédients",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        drink.ingredients().forEach { (ingredient, measure) ->
                            Text(
                                text = "• ${measure?.let { "$it " } ?: ""}$ingredient",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))

                        // Instructions
                        Text(
                            text = "Préparation",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = drink.strInstructions ?: "Aucune instruction disponible.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Bouton principal
                        Button(
                            onClick = {
                                drinkViewModel.logDrink(drink.idDrink, drink.strDrink, drink.strDrinkThumb)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "J'ai bu ce cocktail !",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retour")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun Chip(text: String) {
    androidx.compose.material3.SuggestionChip(
        onClick = {},
        label = {
            Text(text = text, style = MaterialTheme.typography.labelSmall)
        },
        modifier = Modifier.height(28.dp)
    )
}
