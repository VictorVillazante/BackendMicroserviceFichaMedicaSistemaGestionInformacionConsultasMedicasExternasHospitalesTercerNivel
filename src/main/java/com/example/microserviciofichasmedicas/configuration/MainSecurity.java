package com.example.microserviciofichasmedicas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Arrays;
import java.util.List;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {
   
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().configurationSource( request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("*"));
            corsConfiguration.setAllowedOriginPatterns(List.of("*"));
            corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE","PATCH","OPTIONS"));
            corsConfiguration.setAllowCredentials(false);
            corsConfiguration.addExposedHeader("Message");
            corsConfiguration.setAllowedHeaders(List.of("*"));
            return corsConfiguration;
        }).and().csrf().disable()
                .authorizeRequests()
                .requestMatchers("/fichas-medicas/*","/fichas-medicas","/fichas-medicas/paciente/*","/fichas-medicas/medico/*","/fichas-medicas/detalle/paciente/*")
                .permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}