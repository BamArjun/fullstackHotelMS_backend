package com.snsc.spring_HotelMS.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {
	
	private Long id;
	private String email;
	private String name;
	private String role;
	private String phoneNUmber;
	private List<BookingDTO> booking = new ArrayList<>();
	

}
