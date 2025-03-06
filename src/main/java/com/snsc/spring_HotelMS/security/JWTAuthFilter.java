package com.snsc.spring_HotelMS.security;

import com.snsc.spring_HotelMS.service.CustomerUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.snsc.spring_HotelMS.utils.JWTUtils;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CustomerUserDetailService customUserDetailsService;


    @Override		//This method is executed for every HTTP request.

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");	//Retrieves the Authorization header from the request.
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || authHeader.isBlank()) {		//If the header is missing or empty, the request is processed without authentication.
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);		//Extracts only the JWT token.
        userEmail = jwtUtils.extractUsername(jwtToken);//Decodes the JWT token and extracts the user's email/username
        
//		Ensures the user is not already authenticated.
//        Fetches user details (username, roles, permissions) from the database.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            
//            If the token is valid:
//            -	Loads user details.
//            -Authenticates the user.
//            -	Stores authentication in SecurityContext.
            
            if (jwtUtils.isValidToken(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);	//Passes the request to the next filter in the chain.
    }
}