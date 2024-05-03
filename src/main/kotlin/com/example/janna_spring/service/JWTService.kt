package com.example.janna_spring.service

import com.example.janna_spring.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class JwtService {
    @Value("\${token.signing.key}")
    private val jwtSigningKey: String? = null

    fun extractUserName(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        if (userDetails is User) {
            claims["id"] = userDetails.id
            claims["email"] = userDetails.email
            claims["role"] = userDetails.role
        }
        return generateToken(claims, userDetails)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolvers.invoke(claims)
    }

    private fun generateToken(extraClaims: Map<String, Any?>, userDetails: UserDetails): String {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 100000 * 60 * 24))
            .signWith(signingKey, SignatureAlgorithm.HS256).compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private val signingKey: Key
        get() {
            val keyBytes = Decoders.BASE64.decode(jwtSigningKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}