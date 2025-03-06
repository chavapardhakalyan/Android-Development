package com.example.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.data.model.Article
import com.example.newsapp.presentation.navigation.routes.CategoryScreen
import com.example.newsapp.presentation.navigation.routes.HomeScreen
import com.example.newsapp.presentation.screens.CategoryDetailsNews
import com.example.newsapp.presentation.screens.HomeScreenUI
import com.example.newsapp.presentation.viewmodels.NewsAppViewModel

@Composable

fun AppNavigation( viewModel: NewsAppViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreen){
        composable<HomeScreen> {
            HomeScreenUI(navController = navController, viewModel = viewModel)
        }
        composable<CategoryScreen> {
            val categoryScreen = it.toRoute<CategoryScreen>()
            val article = Article(
                author = categoryScreen.author,
                content = categoryScreen.content,
                description = categoryScreen.description,
                publishedAt = categoryScreen.publishedAt,
                source = null,
                title = categoryScreen.title,
                url = categoryScreen.url,
                urlToImage = categoryScreen.urlToImage,
            )
            CategoryDetailsNews(article = article)
        }
    }
}