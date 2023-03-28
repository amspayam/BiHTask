package com.payam.bih.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.payam.bih.android.features.coffeedetail.presentation.CoffeeDetailScreen
import com.payam.bih.android.features.coffeelist.presentation.CoffeeListScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.CoffeeList.path
    ) {
        addGraph(
            navController = navController, navGraphBuilder = this
        )
    }

}

fun addGraph(
    navController: NavHostController, navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.CoffeeList.path) {
        CoffeeListScreen(
            navigateToDetail = { coffeeId ->
                navController.navigate(
                    NavRoute.CoffeeDetail.withArgs(
                        coffeeId.toString()
                    )
                )
            }
        )
    }

    navGraphBuilder.composable(
        route = NavRoute.CoffeeDetail.withArgsFormat(NavRoute.CoffeeDetail.coffeeId),
        arguments = listOf(navArgument(NavRoute.CoffeeDetail.coffeeId) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments

        args?.getInt(NavRoute.CoffeeDetail.coffeeId)?.let {
            CoffeeDetailScreen(
                coffeeId = it
            )
        }
    }
}