package com.example.janna_spring.controller

import com.example.janna_spring.dto.request.LoginRequest
import com.example.janna_spring.dto.request.RegistrationRequest
import com.example.janna_spring.dto.response.JwtAuthenticationResponse
import com.example.janna_spring.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/registration")
    fun signUp(@RequestBody request: RegistrationRequest): JwtAuthenticationResponse {
        return authenticationService.signUp(request)
    }

    @PostMapping("/login")
    fun signIn(@RequestBody request: LoginRequest): JwtAuthenticationResponse {
        return authenticationService.signIn(request)
    }
}