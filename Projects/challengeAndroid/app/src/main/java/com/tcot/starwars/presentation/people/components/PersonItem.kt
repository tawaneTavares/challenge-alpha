package com.tcot.starwars.presentation.people.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tcot.starwars.domain.model.Person
import com.tcot.starwars.domain.model.getUrlImage
import com.tcot.starwars.presentation.theme.StarWarsScreenTheme

@Composable
fun PersonItem(
    person: Person,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp),
        ) {
            AsyncImage(
                model = person.getUrlImage(),
                contentDescription = person.name,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.titleLarge,
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
    }
}

@Preview
@Composable
fun PersonItemPreview() {
    StarWarsScreenTheme {
        PersonItem(
            person = Person(
                "Anakin Skywalker",
                "188",
                "84",
                "fair",
                "https://swapi.dev/api/people/11/",
                11,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}