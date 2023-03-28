package com.payam.bih.android.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.payam.bih.android.R

@Composable
fun TopBar(
    navController: NavController,
    onNavigationIconClick: () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var canPop by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        canPop = controller.previousBackStackEntry != null
    }

    val navigationIcon: (@Composable () -> Unit) =
        {
            if (canPop) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        navigationIcon = navigationIcon
    )
}