package com.example.newsapp.persentation.state



sealed class State <out T>{
    data class Success <T>(val data : T?): State<T>()
    object Loading: State<Nothing>()
   data class Error( val error:String?): State<Nothing>()
}