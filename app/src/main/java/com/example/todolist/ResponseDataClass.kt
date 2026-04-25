package com.example.todolist
data class ResponseDataClass(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)