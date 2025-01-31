package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.untile.NewsApplication
import com.example.newsapp.data.apiService.ApiService
import com.example.newsapp.untile.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
   @Singleton
   @Provides
   fun provideApplication(@ApplicationContext context: ApplicationContext): NewsApplication {
       return context as NewsApplication
   }

   @Singleton
   @Provides
   fun provideContext(@ApplicationContext context: Context):Context{
       return context
   }

   @Singleton
   @Provides
   fun provideOkhttpClient():OkHttpClient{
       return OkHttpClient
           .Builder()
           .readTimeout(30L,TimeUnit.SECONDS)
           .writeTimeout(30L,TimeUnit.SECONDS)
           .build()
   }
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}