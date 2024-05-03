package com.example.janna_spring.controller

import com.example.janna_spring.dto.response.CoinsResponse
import com.example.janna_spring.dto.response.UserResponse
import com.example.janna_spring.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getSelfProfile(): UserResponse {
        return userService.currentUser()
    }

    @GetMapping("/getCoins")
    fun getCoins(): CoinsResponse {
        return userService.getCoins()
    }

    @PostMapping("/addCoins/{coinsValue}")
    fun addCoins(@PathVariable coinsValue: Int): CoinsResponse{
        return userService.addCoins(coinsValue)
    }
}