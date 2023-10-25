package com.example.temperaturerecords.repository

import com.example.temperaturerecords.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findUserEntityByUsername(username: String?): Optional<UserEntity?>
}
