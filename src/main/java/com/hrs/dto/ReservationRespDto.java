package com.hrs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hrs.reservarion.clients.dto.CustomerDto;
import com.hrs.reservarion.clients.dto.HotelDto;
import com.hrs.reservarion.clients.dto.RoomDto;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReservationRespDto {

	private String reservationId;
	private LocalDate startDate;
	private LocalDate endDate;
	private CustomerDto customer;
	private HotelDto hotel;
	private RoomDto room;
	private PaymentDto payment;
	private String roomType;
	private String status;

}