package com.planner.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class ServiceInfo(
    val serviceName: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(GrantedAuthority { "SERVICE" })

    override fun getPassword(): String  = serviceName

    override fun getUsername(): String = serviceName

    override fun isAccountNonExpired(): Boolean  = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
