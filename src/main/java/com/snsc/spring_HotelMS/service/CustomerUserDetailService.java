package com.snsc.spring_HotelMS.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.snsc.spring_HotelMS.exception.OurException;
import com.snsc.spring_HotelMS.repo.UserRepository;
@Service
public class CustomerUserDetailService implements UserDetailsService {
	   @Autowired
	    private UserRepository userRepo;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        return userRepo.findByEmail(username).orElseThrow(() -> new OurException("Username/Email not Found"));
	    }
}

		