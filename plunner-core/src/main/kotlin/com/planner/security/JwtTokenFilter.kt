package com.planner.security

import com.planner.security.JwtService.Companion.SERVICE_CLAIM_NAME
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
            ?.removePrefix("Bearer ")

        if (authHeader != null) {
            val serviceName = jwtService.getClaims(authHeader)[SERVICE_CLAIM_NAME]

            if (serviceName is String && serviceName.isNotBlank()) {
                val auth = ServiceInfo(serviceName)
                    .let { UsernamePasswordAuthenticationToken(it, emptyList<String>(), it.authorities) }
                    .apply { details = WebAuthenticationDetailsSource().buildDetails(request) }
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        filterChain.doFilter(request, response)
    }
}