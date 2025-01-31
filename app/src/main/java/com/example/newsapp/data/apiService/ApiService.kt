package com.example.newsapp.data.apiService

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.untile.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// everything?q=bitcoin&apiKey=f373c8603bb6426ba50b5b4314041314
interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") search:String,
        @Query("language") language: String?,
        @Query("sortBy") categories : String?,
      @Query("domains") newssource : String ="bbc.com",
        @Query("apiKey") apikey : String = API_KEY,
        ):Response<NewsResponse>
}
