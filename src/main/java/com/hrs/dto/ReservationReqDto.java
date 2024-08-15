package com.hrs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReservationReqDto {

	private LocalDate startDate;
	private LocalDate endDate;
	private String customerId;
	private Long hotelId;
	private String roomType;
	private String status;


	private Integer amount;
	private String reservationId;

}