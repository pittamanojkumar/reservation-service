package com.hrs.reservarion.clients.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HotelDto {
	private Long hotelId;
	private String hotelName;
	private String address;
	private List<RoomDto> rooms = new ArrayList<>();

}