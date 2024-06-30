package com.tcot.starwars.presentation.planets.planetlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.tcot.starwars.domain.model.Planet
import com.tcot.starwars.presentation.Screen

@Composable
fun PlanetsList(
    navController: NavController,
    planets: LazyPagingItems<Planet>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(top = 45.dp),
    ) {
        if (planets.loadState.refresh is LoadState.Error) {
            Text(
                text = "Error: " + (planets.loadState.refresh as LoadState.Error).error.message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center),
            )
        }
        if (planets.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navController.popBackStack()
                    },
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 55.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                planets.itemCount,
                planets.itemKey { it.id },
                planets.itemContentType { "contentType" },
            ) { index ->
                val planet = planets[index]

                if (planet != null) {
                    PlanetItem(planet = planet, modifier = Modifier.fillMaxWidth(), onClick = {
                        navController.navigate(Screen.PlanetDetailScreen.route + "/${planet.id}")
                    })
                }
            }

            item {
                if (planets.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}