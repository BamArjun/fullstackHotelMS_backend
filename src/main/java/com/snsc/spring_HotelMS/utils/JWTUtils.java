package com.snsc.spring_HotelMS.utils;

import javax.crypto.SecretKey;

public class JWTUtils {
	
	private static final long EXPIRATION_TIME = 1000*60*24*7;//for 7 days
	
	private final SecretKey key;
	
	public JWTUtils()  {
		this.key = null;
		String secretString ="";
		
	}

}
