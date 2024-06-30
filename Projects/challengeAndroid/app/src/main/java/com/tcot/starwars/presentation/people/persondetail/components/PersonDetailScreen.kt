package com.tcot.starwars.presentation.people.persondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tcot.starwars.R
import com.tcot.starwars.domain.model.Person
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.presentation.people.persondetail.PersonDetailViewModel

@Composable
fun PersonDetailScreen(
    navController: NavController,
    viewModel: PersonDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .absolutePadding(top = 60.dp),
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
                    .align(Alignment.Center),
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.person.url.isNotBlank()) {
            DetailTopSection(
                navController,
                state.person.isFavored,
                onFavoriteClick = {
                    viewModel.favoritePerson(state.person.id)
                },
            )
        }

        DetailInfoSection(
            person = state.person,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 170.dp / 2f,
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
                model = state.person.getUrlImage(),
                contentDescription = state.person.name,
                error = painterResource(id = R.drawable.ic_image_not_found),
                modifier = Modifier
                    .height(150.dp)
                    .offset(y = 20.dp)
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
    person: Person,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .offset(y = 100.dp),
    ) {
        Text(
            text = person.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Height: ${person.height}",
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Mass: ${person.mass}",
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Skin Color: ${person.skinColor}",
            color = Color.LightGray,
        )
    }
}