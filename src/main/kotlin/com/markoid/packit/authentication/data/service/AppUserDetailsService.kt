package com.markoid.packit.authentication.data.service

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class AppUserDetailsService(
    private val authRepository: AuthRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        // Guard against empty username
        if (username == null) throw UsernameNotFoundException("")
        return when (val appUser =
            this.authRepository.getUserByEmail(username) ?: this.authRepository.getDriverByEmail(username)) {
            is UserEntity -> User(appUser.email, appUser.password, emptyList())
            is DriverEntity -> User(appUser.email, appUser.password, emptyList())
            else -> throw UsernameNotFoundException("")
        }
    }

}