package com.tcot.starwars.presentation

sealed class Screen(val route: String) {
    object CategoryListScreen : Screen("category_list_screen")
    object CategoryInfoScreen : Screen("category_info_screen")
}