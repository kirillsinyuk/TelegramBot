package com.kvsinyuk.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key


@Service
class JwtService(
    private val jwtProperties: JwtTokenProperties
) {

    fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .body

    // Use only in tests
    fun generateToken(serviceName: String): String {
        val claims: HashMap<String, Any> = hashMapOf(SERVICE_CLAIM_NAME to serviceName)
        return Jwts.builder()
            .addClaims(claims)
            .setSubject(serviceName)
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getKey(): Key {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.token)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    companion object {
        const val SERVICE_CLAIM_NAME = "serviceName"
    }
}
