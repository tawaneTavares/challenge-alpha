package com.tcot.starwars.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.tcot.starwars.R
import com.tcot.starwars.presentation.categories.CategoriesViewModel
import com.tcot.starwars.presentation.categories.components.CategoriesList
import com.tcot.starwars.presentation.lastviews.LastViewsViewModel
import com.tcot.starwars.presentation.lastviews.components.LastViewList
import com.tcot.starwars.presentation.people.peoplelist.PeopleViewModel
import com.tcot.starwars.presentation.people.peoplelist.components.PeopleList
import com.tcot.starwars.presentation.people.persondetail.PersonDetailViewModel
import com.tcot.starwars.presentation.people.persondetail.components.PersonDetailScreen
import com.tcot.starwars.presentation.planets.planetdetail.PlanetDetailViewModel
import com.tcot.starwars.presentation.planets.planetdetail.components.PlanetDetailScreen
import com.tcot.starwars.presentation.planets.planetlist.PlanetsViewModel
import com.tcot.starwars.presentation.planets.planetlist.components.PlanetsList
import com.tcot.starwars.presentation.theme.BottomBarColor
import com.tcot.starwars.presentation.theme.StarWarsScreenTheme
import dagger.hilt.android.AndroidEntryPoint

private const val INITIAL_ANIMATION_VALUE = 0.4f
private const val FINAL_ANIMATION_VALUE = 0.0f
private const val ANIMATION_DURATION = 500L

private const val PLANET_ARG = "planetId"
private const val PERSON_ARG = "personId"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateSplash()

        enableEdgeToEdge()
        setContent {
            StarWarsScreenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,
                ) {
                    MyBottomAppBar()
                }
            }
        }
    }

    private fun animateSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value.isLoading
            }
            setOnExitAnimationListener { screen ->
                val zoomX =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        INITIAL_ANIMATION_VALUE,
                        FINAL_ANIMATION_VALUE,
                    )

                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = ANIMATION_DURATION
                zoomX.doOnEnd { screen.remove() }

                val zoomY =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        INITIAL_ANIMATION_VALUE,
                        FINAL_ANIMATION_VALUE,
                    )

                zoomY.duration = ANIMATION_DURATION
                zoomY.doOnEnd { screen.remove() }

                zoomY.start()
                zoomX.start()
            }
        }
    }
}

@Composable
fun MyBottomAppBar() {
    val navController = rememberNavController()
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = BottomBarColor,
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Home
                        navController.navigate(Screen.CategoryListScreen.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Home) Color.White else Color.Gray,
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Favorite
                        navController.navigate(Screen.CategoryPeopleScreen.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Favorite) Color.White else Color.Gray,
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Star
                        navController.navigate(Screen.LastViewScreen.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_eye_icon),
                        contentDescription = "Last Views",
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Star) Color.White else Color.Gray,
                    )
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.CategoryListScreen.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(
                route = Screen.CategoryListScreen.route,
            ) {
                CategoriesList(navController = navController)
            }
            composable(
                route = Screen.CategoryPeopleScreen.route,
            ) {
                val viewModel = hiltViewModel<PeopleViewModel>()
                val people = viewModel.peoplePagingFlow.collectAsLazyPagingItems()
                PeopleList(navController = navController, people = people)
            }
            composable(
                route = Screen.CategoryPlanetScreen.route,
            ) {
                val viewModel = hiltViewModel<PlanetsViewModel>()
                val planets = viewModel.planetPagingFlow.collectAsLazyPagingItems()
                PlanetsList(navController = navController, planets = planets)
            }

            composable(
                route = Screen.PlanetDetailScreen.route + "/{$PLANET_ARG}",
                arguments = listOf(
                    navArgument(PLANET_ARG) {
                        type = NavType.IntType
                    },
                ),
            ) {
                val planetId = remember {
                    it.arguments?.getInt(PLANET_ARG) ?: 0
                }
                val viewModel = hiltViewModel<PlanetDetailViewModel>()
                viewModel.getPlanetDetail(planetId)
                PlanetDetailScreen(navController = navController)
            }

            composable(
                route = Screen.PersonDetailScreen.route + "/{$PERSON_ARG}",
                arguments = listOf(
                    navArgument(PERSON_ARG) {
                        type = NavType.IntType
                    },
                ),
            ) {
                val personId = remember {
                    it.arguments?.getInt(PERSON_ARG) ?: 0
                }
                val viewModel = hiltViewModel<PersonDetailViewModel>()
                viewModel.getPersonDetail(personId)
                PersonDetailScreen(navController = navController)
            }

            composable(
                route = Screen.LastViewScreen.route,
            ) {
                val viewModel = hiltViewModel<LastViewsViewModel>()
                viewModel.getLastViews()
                LastViewList(navController = navController, viewModel = viewModel)
            }
        }
    }
}
