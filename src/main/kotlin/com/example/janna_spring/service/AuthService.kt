package com.example.janna_spring.service

import com.example.janna_spring.dto.response.JwtAuthenticationResponse
import com.example.janna_spring.dto.request.LoginRequest
import com.example.janna_spring.dto.request.RegistrationRequest
import com.example.janna_spring.entity.Role
import com.example.janna_spring.entity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {
    fun signUp(request: RegistrationRequest): JwtAuthenticationResponse {

        val user = User(
//            1L,
//            UUID.randomUUID().toString(),
//            0L,
            request.username,
            passwordEncoder.encode(request.password),
            request.email,
            userService.starterCoins(),
            Role.ROLE_USER,
        )
        userService.create(user)
        val jwt = jwtService.generateToken(user)
        return JwtAuthenticationResponse(jwt)
    }

    fun signIn(request: LoginRequest): JwtAuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userService
            .userDetailsService()
            .loadUserByUsername(request.email)

        val jwt = jwtService.generateToken(user)
        return JwtAuthenticationResponse(jwt)
    }
}