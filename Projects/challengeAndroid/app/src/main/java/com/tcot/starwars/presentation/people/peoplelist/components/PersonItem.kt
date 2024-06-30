package com.tcot.starwars.presentation.people.peoplelist.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tcot.starwars.R
import com.tcot.starwars.domain.model.Person
import com.tcot.starwars.domain.model.getUrlImage

@Composable
fun PersonItem(
    person: Person,
    modifier: Modifier,
    onClick: (Person) -> Unit,
) {
    Card(
        modifier = modifier.clickable {
            onClick(person)
        },
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
                    text = person.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Height: ${person.height}",
                    color = Color.DarkGray,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Mass: ${person.mass}",
                    color = Color.DarkGray,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 13.sp,
                    text = "Skin Color: ${person.skinColor}",
                    color = Color.DarkGray,
                )
            }
        }
    }
}