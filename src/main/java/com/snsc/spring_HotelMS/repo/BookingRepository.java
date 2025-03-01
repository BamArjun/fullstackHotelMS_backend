package com.snsc.spring_HotelMS.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.snsc.spring_HotelMS.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByRoomId(long roomId);
	
    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

	List<Booking> findByUserId(long userId);

}
