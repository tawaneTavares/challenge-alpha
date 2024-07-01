package com.tcot.starwars.presentation.lastviews.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tcot.starwars.presentation.lastviews.LastViewsViewModel

@Composable
fun LastViewList(
    navController: NavController,
    viewModel: LastViewsViewModel = hiltViewModel(),
) {
}