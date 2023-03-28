package com.payam.bih.android.features.coffeelist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.presentation.components.CoffeeListItem


@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {

    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun CoffeeListScreen(
    viewModel: CoffeeListViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit
) {

    viewModel.getCoffeeList()

    val state = viewModel.coffeeListState.collectAsState().value

    val lifecycleState = LocalLifecycleOwner.current.lifecycle.observeAsState()

    LaunchedEffect(lifecycleState.value) {
        when (lifecycleState.value) {
            Lifecycle.Event.ON_RESUME -> viewModel.updateList()
            else -> {}
        }
    }

    Content(
        state = state,
        onRefresh = {
            viewModel.getCoffeeList()
        },
        navigateToDetail = navigateToDetail
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Content(
    state: CoffeeListState,
    onRefresh: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    val swipeRefreshState = rememberPullRefreshState(state.isLoading, onRefresh)
    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(swipeRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            state.coffeeList?.let { it ->
                items(
                    it,
                    key = {
                        it.coffeeId
                    }
                ) { coffee ->
                    CoffeeListItem(
                        coffee = coffee,
                        onItemClick = navigateToDetail
                    )
                }
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

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isLoading,
            state = swipeRefreshState,
            contentColor = MaterialTheme.colors.secondary
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeListScreenPreview() {
    Content(state = CoffeeListState(
        isLoading = false,
        coffeeList = listOf(
            CoffeeEntity(
                coffeeId = 1,
                coffeeTitle = "Galão",
                coffeeDescription = "Originating in Portugal.",
                coffeeImageLink = "",
                ingredients = listOf("Coffee", "Sugar"),
                isFavorite = false
            ),
            CoffeeEntity(
                coffeeId = 1,
                coffeeTitle = "Lungo",
                coffeeDescription = "A lungo is a long-pull espresso.",
                coffeeImageLink = "",
                ingredients = listOf("Coffee", "Sugar"),
                isFavorite = false
            )
        )
    ), onRefresh = {}, navigateToDetail = {})
}