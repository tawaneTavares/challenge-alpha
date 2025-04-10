package com.tcot.starwars.presentation.planets.planetlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tcot.starwars.R
import com.tcot.starwars.domain.model.Planet
import com.tcot.starwars.domain.model.getUrlImage

@Composable
fun PlanetItem(
    planet: Planet,
    modifier: Modifier,
    onClick: (Planet) -> Unit,
) {
    Card(
        modifier = modifier.clickable {
            onClick(planet)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp),
        ) {
            AsyncImage(
                model = planet.getUrlImage(),
                contentDescription = planet.name,
                error = painterResource(id = R.drawable.ic_image_not_found),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Climate: ${planet.climate}",
                    color = Color.DarkGray,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Population: ${planet.population}",
                    color = Color.DarkGray,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Terrain: ${planet.terrain}",
                    color = Color.DarkGray,
                )
            }
        }
    }
}