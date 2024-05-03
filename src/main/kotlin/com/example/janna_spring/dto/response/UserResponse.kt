package com.example.janna_spring.dto.response

data class UserResponse (
    val id: Long,
    val email: String,
    val username: String,
    val coins: Int,
)
