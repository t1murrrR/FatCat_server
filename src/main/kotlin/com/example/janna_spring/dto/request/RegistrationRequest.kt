package com.example.janna_spring.dto.request


data class RegistrationRequest(
    val username: String,
    val email: String,
    val password: String
)