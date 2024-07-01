package com.tcot.starwars.presentation.lastviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.tcot.starwars.common.Constants.TYPE_PEOPLE
import com.tcot.starwars.common.Constants.TYPE_PLANETS
import com.tcot.starwars.domain.model.LastView
import com.tcot.starwars.presentation.Screen
import com.tcot.starwars.presentation.lastviews.LastViewsViewModel

@Composable
fun LastViewList(
    navController: NavController,
    viewModel: LastViewsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            items(state.lastViews) { lastViews ->
                LastViewItem(lastView = lastViews, onClick = {
                    when (lastViews.type) {
                        TYPE_PEOPLE -> {
                            navController.navigate(Screen.PersonDetailScreen.route + "/${lastViews.id}")
                        }
                        TYPE_PLANETS -> {
                            navController.navigate(Screen.PlanetDetailScreen.route + "/${lastViews.id}")
                        }
                    }
                })
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center),
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun LastViewItem(
    lastView: LastView,
    onClick: (LastView) -> Unit,
) {
    Row(
        modifier =
        Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .clickable {
                onClick(lastView)
            }
            .size(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .paint(
                painter = rememberAsyncImagePainter(lastView.imageUrl),
                contentScale = ContentScale.FillBounds,
            )
            .clipToBounds()
            .border(1.dp, Color(0xFFEEEEEE), shape = RoundedCornerShape(10.dp)),
    ) {
        Text(
            lastView.name,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color(0x88EEEEEE))
                .fillMaxWidth()
                .align(Alignment.Bottom),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}