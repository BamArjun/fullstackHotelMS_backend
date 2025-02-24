package com.snsc.spring_HotelMS.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of adults must be at least 1.")
    private int numOfAdults;

    @Min(value = 0, message = "Number of children cannot be negative.")
    private int numOfChildren;

    private int totalNumOfGuest;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public void calculateTotalNumOfGuests() {
        this.totalNumOfGuest = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumOfGuests();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumOfGuests();
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
                + ", numOfAdults=" + numOfAdults + ", numOfChildren=" + numOfChildren + ", totalNumOfGuest="
                + totalNumOfGuest + ", bookingConfirmationCode=" + bookingConfirmationCode + "]";
    }
}
