package com.payam.bih.android.features.coffeedetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.payam.bih.android.R
import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.presentation.components.CoffeeDetailReviewView

@Composable
fun CoffeeDetailScreen(
    viewModel: CoffeeDetailViewModel = hiltViewModel(),
    coffeeId: Int
) {

    viewModel.getCoffeeDetail(coffeeId = coffeeId)

    val state = viewModel.coffeeDetailState.collectAsState().value

    CoffeeDetailView(
        state = state,
        onFavorite = { viewModel.favoriteCoffee(coffee = it) },
        onReviewSubmit = { userName, date, rating, desc ->
            viewModel.submitReview(
                userName = userName,
                date = date,
                rating = rating,
                desc = desc
            )
        }
    )

}

@Composable
fun CoffeeDetailView(
    state: CoffeeDetailState,
    onFavorite: (Coffee: CoffeeEntity) -> Unit,
    onReviewSubmit: (String, String, String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        state.coffee?.let { coffee ->
            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {
                CoffeeDetailContent(
                    coffee = coffee,
                    onFavorite = { onFavorite(coffee) },
                    onReviewSubmit = onReviewSubmit
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier.wrapContentSize().padding(horizontal = 20.dp)
                    .align(Alignment.Center),
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}

@Composable
fun CoffeeDetailContent(
    coffee: CoffeeEntity,
    onFavorite: () -> Unit,
    onReviewSubmit: (String, String, String, String) -> Unit
) {
    HeaderContent(
        imageLink = coffee.coffeeImageLink
    )
    BodyContent(
        coffee = coffee,
        onFavorite = onFavorite,
        onReviewSubmit = onReviewSubmit
    )
}

@Composable
fun HeaderContent(
    imageLink: String
) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(2.dp)),
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current).data(imageLink)
                .size(Size.ORIGINAL).build()
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BodyContent(
    coffee: CoffeeEntity,
    onFavorite: () -> Unit,
    onReviewSubmit: (String, String, String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = coffee.coffeeTitle,
            style = MaterialTheme.typography.h4,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = coffee.coffeeDescription,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Ingredients : ${coffee.ingredients.joinToString()}",
            style = MaterialTheme.typography.body2,
            color = Color.DarkGray
        )
        Icon(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
                .clickable {
                    onFavorite()
                },
            painter = painterResource(
                id = if (coffee.isFavorite)
                    R.drawable.ic_favorite_32
                else R.drawable.ic_favorite_border_32
            ),
            tint = Color.Red,
            contentDescription = "Favorite"
        )
        Divider(modifier = Modifier.height(4.dp))
        CoffeeDetailReviewView(
            onReviewSubmit = onReviewSubmit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeDetailScreenPreview() {
    CoffeeDetailView(
        state = CoffeeDetailState(
            isLoading = false, coffee = CoffeeEntity(
                coffeeId = 1,
                coffeeTitle = "Galão",
                coffeeDescription = "Originating in Portugal.",
                coffeeImageLink = "",
                ingredients = listOf("Coffee", "Sugar"),
                isFavorite = true
            ), error = ""
        ),
        onFavorite = {},
        onReviewSubmit = { _, _, _, _ -> }
    )
}