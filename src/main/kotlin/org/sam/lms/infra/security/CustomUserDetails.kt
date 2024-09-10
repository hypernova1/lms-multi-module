package org.sam.lms.infra.security

import org.sam.lms.account.domain.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(val account: Account) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority(account.role.toString()))
    }

    override fun getPassword(): String {
        return account.password
    }

    override fun getUsername(): String {
        return account.email
    }
}