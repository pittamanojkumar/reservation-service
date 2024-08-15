package com.hrs.reservarion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="reservation")
public class Reservation {
	@Id
	@Column(name="reservation_id")
	private String reservationId;
	
	@Column(name="start_date")
	private LocalDate  startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;

	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="hotel_id")
	private Long hotelId;

	@Column(name="room_number")
	private String roomNumber;

	@Column(name="room_type")
	private String roomType;

	@Column(name="status")
	private String status;

	@Column(name="expiry_date")
	private ZonedDateTime expiryDate;

}