package com.snsc.spring_HotelMS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.snsc.spring_HotelMS.service.CustomerUserDetailService;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private CustomerUserDetailService customUserDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)	//CSRF (Cross-Site Request Forgery) is disabled because JWT handles authentication instead of cookies.
                .cors(Customizer.withDefaults())	//Allows frontend applications (like React, Angular) to make requests.
                
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/rooms/**", "/bookings/**").permitAll()  //Public endpoints (No authentication needed).
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Spring Security won't create sessions
                .authenticationProvider(authenticationProvider())   //Uses a custom authentication provider for user validation.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  //The JWTAuthFilter is executed before the default Spring Security authentication.

        return httpSecurity.build();
    }

    @Bean
    //Uses DaoAuthenticationProvider (Spring's default provider for authenticating with a database).
//    setUserDetailsService(customUserDetailsService): Uses the custom user service to load user details.
//    setPasswordEncoder(passwordEncoder()): Uses BCrypt to hash passwords.
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
//    BCryptPasswordEncoder(): Ensures passwords are hashed securely before saving in the database.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
//    Manages user authentication requests.
//    Used in login logic to validate username and password.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
