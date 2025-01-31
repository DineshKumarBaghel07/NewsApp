package com.example.newsapp.data.Repository

import com.example.newsapp.data.apiService.ApiService
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.domain.Repositry.respositry.NewsRepo
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: ApiService) : NewsRepo {
    override suspend fun getNews(
      search:String,
      language:String?,
      category:String?
    ): Response<NewsResponse> {
       return api.getNews(
          search,language,category
        )
    }
}