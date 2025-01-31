package com.example.newsapp.di

import com.example.newsapp.data.apiService.ApiService
import com.example.newsapp.data.Repository.RepositoryImpl
import com.example.newsapp.domain.Repositry.respositry.NewsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository( api: ApiService): NewsRepo {
        return RepositoryImpl(api)
    }
}