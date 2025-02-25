package com.snsc.spring_HotelMS.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snsc.spring_HotelMS.model.Booking;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {
	
	private Long id;
	private String email;
	private String name;
	private String role;
	private String phoneNUmber;
	private List<Booking> booking = new ArrayList<>();
	

}
