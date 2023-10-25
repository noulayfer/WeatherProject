package com.example.temperaturerecords.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic()
            .and()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST,"/weather").hasAnyRole("EDITOR")
            .anyRequest().permitAll()
            .and().csrf().disable()
            .cors().disable().build()
    }
}
