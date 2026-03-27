package com.supdevinci.picoleurbattleroyaledition.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.supdevinci.picoleurbattleroyaledition.data.local.entities.FavoriteEntity
import com.supdevinci.picoleurbattleroyaledition.viewmodel.DrinkViewModel

@Composable
fun FavoriteCocktailScreen(
    onCocktailSelected: (String) -> Unit,
    onBack: () -> Unit,
    drinkViewModel: DrinkViewModel = viewModel()
) {
    val favoritesWithCounts by drinkViewModel.favoritesWithCounts.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mes favoris",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${favoritesWithCounts.size} cocktail${if (favoritesWithCounts.size > 1) "s" else ""}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (favoritesWithCounts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Aucun favori pour l'instant.\nTrouvez un cocktail et appuyez sur ♡ !",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(favoritesWithCounts, key = { it.first.cocktailId }) { (favorite, count) ->
                    FavoriteCard(
                        favorite = favorite,
                        drinkCount = count,
                        onClick = { onCocktailSelected(favorite.cocktailId) },
                        onRemove = { drinkViewModel.toggleFavorite(favorite.cocktailId, favorite.name, favorite.thumb) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteCard(
    favorite: FavoriteEntity,
    drinkCount: Int,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = favorite.thumb,
                contentDescription = favorite.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favorite.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (drinkCount == 0) "Jamais bu" else "Bu $drinkCount fois",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (drinkCount > 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Retirer des favoris",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
