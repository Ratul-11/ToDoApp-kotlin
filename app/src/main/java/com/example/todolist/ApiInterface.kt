package com.example.todolist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("todos/1")
    fun getData(): Call<ResponseDataClass>
}