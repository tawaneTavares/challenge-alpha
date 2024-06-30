package com.tcot.starwars.presentation

sealed class Screen(val route: String) {
    object CategoryListScreen : Screen("category_list_screen")
    object CategoryPeopleScreen : Screen("category_people_screen")
    object CategoryPlanetScreen : Screen("category_planet_screen")
    object PlanetDetailScreen : Screen("planet_detail_screen")
    object PersonDetailScreen : Screen("person_detail_screen")
}