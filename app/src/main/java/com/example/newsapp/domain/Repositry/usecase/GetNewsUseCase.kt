package com.example.newsapp.domain.Repositry.usecase

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.Repository.RepositoryImpl
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepo:RepositoryImpl) {
    suspend operator fun invoke(
      search:String,
      language:String?,
      category: String?
    ): NewsResponse {
        val result = newsRepo.getNews(search,language,category)
        if(result.body() == null){
            if (result.code() == 400){
            throw Exception("Bad Request")
            }
            else if(result.code() ==401){
                throw Exception("Unauthorized")
            }
            else if(result.code()==405){
                throw Exception("Not Found")
            }
            else if(result.code()==500){
                throw Exception("Internal Error")
            }
        }
        return result.body()!!
    }

}