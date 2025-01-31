package com.example.newsapp.domain.Repositry.respositry


import com.example.newsapp.data.model.NewsResponse

interface NewsRepo {

    suspend fun getNews(
      search:String,
      language:String?,
      category:String?
    ):retrofit2.Response<NewsResponse>
}