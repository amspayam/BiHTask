package com.payam.bih.android.features.coffeelist.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.payam.bih.android.R
import com.payam.bih.android.db.entity.CoffeeEntity

@Composable
fun CoffeeListItem(
    coffee: CoffeeEntity, onItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onItemClick(coffee.coffeeId) }
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(0.3f)
                    .aspectRatio(1f).clip(RoundedCornerShape(2.dp)),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(coffee.coffeeImageLink)
                        .size(Size.ORIGINAL).build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = coffee.coffeeTitle,
                    style = MaterialTheme.typography.h4,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = coffee.coffeeDescription,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = Color.Gray
                )
                if (coffee.isFavorite)
                    Icon(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(8.dp),
                        painter = painterResource(
                            id = R.drawable.ic_favorite_18
                        ),
                        tint = Color.Red,
                        contentDescription = "Favorite"
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoffeeListItem(coffee = CoffeeEntity(
        coffeeId = 1,
        coffeeTitle = "Galão",
        coffeeDescription = "Originating in Portugal.",
        coffeeImageLink = "",
        ingredients = listOf("Coffee", "Sugar"),
        isFavorite = false
    ), onItemClick = {})
}