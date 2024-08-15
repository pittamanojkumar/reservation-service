package com.hrs.reservarion.clients.api;

import com.hrs.reservarion.clients.dto.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HotelManagementClient {

    @Autowired
    private WebClient webClient;

    @Value("${gateway-server.baseUrl}")
    private String gateWayServerUrl;

    public HotelDto getHotelById(Long hotelId) {
        return webClient.get()
                .uri(gateWayServerUrl + "/hotel-management-service/api/v1/hotel/{id}", hotelId)
                .retrieve()
                .bodyToMono(HotelDto.class)
                .block();
    }

}
