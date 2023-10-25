package com.example.temperaturerecords.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    var id: Long? = null,

    @Column(name = "username", unique = true, nullable = false)
    private var username: @Size(
        max = 15,
        message = "Username must be between 3 and 15 characters",
        min = 3
    ) String,

    @Column(name = "password", nullable = false)
    private var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role? = null
) : UserDetails {
    enum class Role : GrantedAuthority {
        EDITOR;

        override fun getAuthority(): String {
            return "ROLE_$name"
        }
    }

    override fun getAuthorities(): List<Role?> {
        return listOf(role)
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
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