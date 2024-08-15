package com.hrs.reservarion.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDto {

	private String customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

}