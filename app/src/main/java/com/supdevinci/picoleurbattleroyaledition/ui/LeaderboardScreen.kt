package com.supdevinci.picoleurbattleroyaledition.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supdevinci.picoleurbattleroyaledition.viewmodel.DrinkViewModel

private data class LeaderboardEntry(
    val name: String,
    val initials: String,
    val drinksThisWeek: Int,
    val isCurrentUser: Boolean = false
)

@Composable
fun LeaderboardScreen(drinkViewModel: DrinkViewModel = viewModel()) {
    val drinksThisWeek by drinkViewModel.drinksThisWeek.collectAsStateWithLifecycle()
    val myCount = drinksThisWeek.size

    val mockEntries = listOf(
        LeaderboardEntry("Sophie M.", "SM", 15),
        LeaderboardEntry("Thomas B.", "TB", 11),
        LeaderboardEntry("Léa C.", "LC", 8),
        LeaderboardEntry("Nathan D.", "ND", 6),
        LeaderboardEntry("Emma R.", "ER", 4),
    )

    val allEntries = (mockEntries + LeaderboardEntry("Vous", "ME", myCount, isCurrentUser = true))
        .sortedByDescending { it.drinksThisWeek }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Leaderboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Cocktails bus cette semaine",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(allEntries) { index, entry ->
                LeaderboardRow(rank = index + 1, entry = entry)
            }
        }
    }
}

@Composable
private fun LeaderboardRow(rank: Int, entry: LeaderboardEntry) {
    val medalColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(if (entry.isCurrentUser) 4.dp else 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (entry.isCurrentUser)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rang
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(medalColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#$rank",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            // Avatar initiales
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = entry.initials,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            // Nom
            Text(
                text = entry.name + if (entry.isCurrentUser) " (moi)" else "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (entry.isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            // Compte
            Text(
                text = "${entry.drinksThisWeek} 🍹",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (entry.isCurrentUser)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
