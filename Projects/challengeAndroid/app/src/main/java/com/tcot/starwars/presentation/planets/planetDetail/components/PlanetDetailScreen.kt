package com.tcot.starwars.presentation.planets.planetdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tcot.starwars.R
import com.tcot.starwars.domain.model.Planet
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.presentation.planets.planetdetail.PlanetDetailViewModel

@Composable
fun PlanetDetailScreen(
    navController: NavController,
    viewModel: PlanetDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Center),
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        }

        if (state.planet.url.isNotBlank()) {
            DetailTopSection(
                navController,
                state.planet.isFavored,
                onFavoriteClick = {
                    viewModel.favoritePlanet(state.planet.id)
                },
            )
        }

        DetailInfoSection(
            planet = state.planet,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 190.dp / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(16.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AsyncImage(
                model = state.planet.getUrlImage(),
                contentDescription = state.planet.name,
                error = painterResource(id = R.drawable.ic_image_not_found),
                modifier = Modifier
                    .height(150.dp)
                    .offset(y = 30.dp)
                    .clip(shape = CircleShape)
                    .border(2.dp, Color.White, CircleShape),
            )
        }
    }
}

@Composable
fun DetailTopSection(
    navController: NavController,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {
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

        var favoredChange by remember {
            mutableStateOf(isFavorite)
        }
        Icon(
            imageVector = if (favoredChange) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    favoredChange = !favoredChange
                    onFavoriteClick()
                },
        )
    }
}

@Composable
fun DetailInfoSection(
    planet: Planet,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = modifier
            .fillMaxHeight(0.5f)
            .offset(y = 100.dp),
    ) {
        Text(
            text = planet.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "climate: ${planet.climate}",
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "population: ${planet.population}",
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "terrain: ${planet.terrain}",
            color = Color.LightGray,
        )

        // TODO finish detail screen
//        Row {
//            UniqueInfo(
//                planet = planet,
//                modifier = Modifier,
//            )
//        }
    }
}

@Composable
fun UniqueInfo(
    planet: Planet,
    modifier: Modifier,
) {
    Box(
        contentAlignment = Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0x51D10000))
            .padding(16.dp),
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = planet.name,
                modifier = Modifier
                    .size(36.dp)
                    .align(CenterHorizontally),
            )
            Text(
                text = "Terrain",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Text(
                text = planet.terrain,
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}