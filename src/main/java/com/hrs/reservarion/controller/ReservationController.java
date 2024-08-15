package com.hrs.reservarion.controller;

import com.hrs.dto.ReservationReqDto;
import com.hrs.dto.ReservationRespDto;
import com.hrs.reservarion.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservation")
@Slf4j
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@PostMapping("/create")
	public ReservationReqDto createReservation(@RequestBody ReservationReqDto reqDto) {
		log.info("Received Reservation Request");
		return reservationService.createReservation(reqDto);
	}

	@GetMapping("/{reservation-id}")
	public ReservationRespDto getReservationsById(@PathVariable("reservation-id") String reservationId) {
		log.info("Received getReservationsById Request");
		return reservationService.getReservationsById(reservationId);
	}

	@GetMapping("/by/hotel/{hotel-id}")
	public List<ReservationRespDto> getReservationsByHotel(@PathVariable("hotel-id") Long hotelId) {
		log.info("Received getReservationsByHotel Request");
		return reservationService.getReservationsByHotel(hotelId);
	}

	@GetMapping("/by/customer/{customer-id}")
	public List<ReservationRespDto> getReservationsByCustomer(@PathVariable("customer-id") String customerId) {
		log.info("Received getReservationsByCustomer Request");
		return reservationService.getReservationsByCustomer(customerId);
	}


}