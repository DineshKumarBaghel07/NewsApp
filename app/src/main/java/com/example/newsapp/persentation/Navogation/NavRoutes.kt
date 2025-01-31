package com.example.newsapp.persentation.Navogation

import com.example.newsapp.data.model.Article
import kotlinx.serialization.Serializable

sealed class NavRoutes{
    @Serializable
    object Home

    @Serializable
    data class NewsDetails(val news: Article)

}