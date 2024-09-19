package org.sam.gateway.security

import org.sam.gateway.account.Account
import org.sam.gateway.account.AccountWithRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(val accountWithRole: AccountWithRole) : UserDetails {

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority(accountWithRole.role.name))
    }

    override fun getPassword(): String {
        return accountWithRole.account.password
    }

    override fun getUsername(): String {
        return accountWithRole.account.id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}