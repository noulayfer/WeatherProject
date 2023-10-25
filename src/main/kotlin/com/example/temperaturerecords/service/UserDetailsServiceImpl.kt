package com.example.temperaturerecords.service

import com.example.temperaturerecords.config.PasswordConfig
import com.example.temperaturerecords.entities.UserEntity
import com.example.temperaturerecords.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val userRepository: UserRepository, val passwordConfig: PasswordConfig) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserEntityByUsername(username)
            .orElseThrow { UsernameNotFoundException("Username not found with that name") }
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        val userRole = user?.role
        if (userRole == UserEntity.Role.EDITOR) {
            authorities.add(SimpleGrantedAuthority("ROLE_EDITOR"))
        } else {
            authorities.add(SimpleGrantedAuthority("ROLE_AUTHORIZED"))
        }
        return User(
            user!!.username,
            passwordConfig.passwordEncoder().encode(user.password),
            true,
            true,
            true,
            true,
            authorities
        )
    }
}
