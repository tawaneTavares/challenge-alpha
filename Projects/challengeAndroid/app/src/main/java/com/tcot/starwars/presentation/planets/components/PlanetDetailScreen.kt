package com.tcot.starwars.presentation.planets.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tcot.starwars.presentation.planets.PlanetsViewModel

@Composable
fun PlanetDetailScreen(
    navController: NavController,
    viewModel: PlanetsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        DetailTopSection(
            navController,
        )
    }
}

@Composable
fun DetailTopSection(
    navController: NavController,
) {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .absolutePadding(top = 60.dp)
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                },
        )
    }
}