package com.example.janna_spring.util

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

/*
@Service
class AuthenticationManagerImpl: AuthenticationManager {
    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication!!.principal
        val password = authentication.credentials

        if (false) {
            throw BadCredentialsException("Нет такого пользователя!!")
        }

        authentication.isAuthenticated = true
        return authentication
    }
}

 */