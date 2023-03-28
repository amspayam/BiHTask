package com.payam.bih.android.navigation

sealed class NavRoute(val path: String) {

    object CoffeeList : NavRoute("coffee_list")
    object CoffeeDetail : NavRoute("coffee_detail") {
        const val coffeeId = "coffee_id"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }

}
