package com.snsc.spring_HotelMS.utils;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTUtils {
	
	//Constants and Secret Key Setup.EXPIRATION_TIME: Defines how long the token is valid (7 days in milliseconds).
	//key: A secret key used to sign and verify tokens.
	private static final long EXPIRATION_TIME = 1000*60*24*7;//for 7 days
	
	private final SecretKey key;
	
	
//	This constructor creates a secret key from a long encoded string.Secret Key Initialization
//	The key is used to sign and verify JWT tokens.
//	HmacSHA256 is the algorithm used for security.
	
	  public JWTUtils() {
	        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
	        byte[] keyBytes = java.util.Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
	        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");

	    }
	  
//	  This method creates a JWT token that contains:
//		  Username (so the server knows who the user is).
//		  Issued time (when the token was created).
//		  Expiration time (after which the token is invalid).
//		  A digital signature (to ensure it hasn’t been tampered with).

	    public String generateToken(UserDetails userDetails) {
	        return Jwts.builder()
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(key)
	                .compact();
	    }
	    
	    
//	    Extracts the username from the JWT.
	    
	    public String extractUsername(String token) {
	        return extractClaims(token, Claims::getSubject);
	    }

	    
//	    A helper method to extract any piece of information (like username or expiration time) from the token.
	    
	    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
	        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
	    }

	    
//	    The username in the token matches the username in UserDetails.
//	    The token is not expired.
	    public boolean isValidToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
//	Extracts the expiration time and checks if it’s before the current time.

	    private boolean isTokenExpired(String token) {
	        return extractClaims(token, Claims::getExpiration).before(new Date());
	    }
}
