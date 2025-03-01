package com.snsc.spring_HotelMS.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.snsc.spring_HotelMS.model.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {
	
	@Query("SELECT DISTINCT r.roomType FROM Room r")
	List<String>findDistinctRoomTypes();			// This method fetches unique room types from the Room table.
	
	
	// Finds free rooms of a specific type in a given date range(checkInDate checkOutDate).
	@Query("SELECT r FROM Room r WHERE r.roomType LIKE :roomType AND r.id NOT IN " +
		       "(SELECT bk.room.id FROM Booking bk WHERE " +
		       "(bk.checkInDate <= :checkOutDate AND bk.checkOutDate >= :checkInDate))")
		List<Room> findAvailableRoomsByDatesAndTypes(@Param("checkInDate") LocalDate checkInDate, 
		                                             @Param("checkOutDate") LocalDate checkOutDate, 
		                                             @Param("roomType") String roomType);

	//Gets all rooms that have never been booked.
	@Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b) ")
	List<Room> getAllAvailableRooms();

}
