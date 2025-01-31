package com.example.newsapp.persentation.Navogation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.data.model.Article
import com.example.newsapp.persentation.Home.HomeScreen
import com.example.newsapp.persentation.Home.NewsViewModel
import com.example.newsapp.persentation.NewsDetails.NewsDetailsScreen
import kotlin.reflect.typeOf

@Composable
fun Navigation(viewModel: NewsViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Home) {
      composable<NavRoutes.Home>{
          HomeScreen(navController = navController)
      }
        composable<NavRoutes.NewsDetails>(
            typeMap = mapOf(typeOf<Article>() to NavRouteDetails.customNewsDetails)
        ){BackStackEntery ->
         val news = BackStackEntery.toRoute<NavRoutes.NewsDetails>().news
            NewsDetailsScreen(navController = navController, news = news)
        }

    }
}
