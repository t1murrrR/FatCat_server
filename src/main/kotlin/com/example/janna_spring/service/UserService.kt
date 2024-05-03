package com.example.janna_spring.service

import com.example.janna_spring.dto.response.CoinsResponse
import com.example.janna_spring.dto.response.UserResponse
import com.example.janna_spring.entity.Role
import com.example.janna_spring.entity.User
import com.example.janna_spring.repository.UserRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(
    private val repository: UserRepository,
    val db: JdbcTemplate
//    private val coinValue: Int,
) {

    fun save(user: User): User {
        return repository.save(user)
    }

    fun create(user: User): User {
        if (repository.existsByUsername(user.getUsername())) {
            throw RuntimeException("Пользователь с таким именем уже существует")
        }
        if (repository.existsByEmail(user.email)) {
            throw RuntimeException("Пользователь с таким email уже существует")
        }
        return save(user)
    }

    private fun getByUsername(username: String?): User {
        return repository.findByUsername(username)!!
    }
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username: String? -> getByUsername(username) }
    }

    fun currentUser(): UserResponse {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = getByUsername(username)
        return UserResponse(user.id, user.email, user.username, user.coins)
    }

    fun starterCoins(): Int{
        return 500
    }

    fun getCoins(): CoinsResponse {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = getByUsername(username)
        return CoinsResponse(user.coins);
    }

    fun addCoins(coinValue: Int): CoinsResponse {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = getByUsername(username)
        user.coins += coinValue;
        val id = user.id
        db.update(
            "update users set coins = ? where id = ?",
            user.coins, id
        )
        return CoinsResponse(user.coins)
    }

}