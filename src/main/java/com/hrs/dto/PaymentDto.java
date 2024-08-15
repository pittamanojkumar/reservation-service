package com.hrs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentDto {


	private String paymentId;

	private String reservationId;

	private LocalDate paymentDate;
	
	private String customerId;
	
	private Long hotelId;

	private Integer amount;

	private String status;


}