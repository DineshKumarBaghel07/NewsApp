package com.example.newsapp.persentation.Navogation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.newsapp.data.model.Article
import kotlinx.serialization.json.Json

object NavRouteDetails {

    val customNewsDetails = object : NavType<Article>(isNullableAllowed = true) {

        override fun get(bundle: Bundle, key: String): Article? {
            val json = bundle.getString(key) ?: return null
            return try {
                Json.decodeFromString<Article>(json)
            } catch (e: Exception) {
                null
            }
        }

        override fun parseValue(value: String): Article {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Article) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Article): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}