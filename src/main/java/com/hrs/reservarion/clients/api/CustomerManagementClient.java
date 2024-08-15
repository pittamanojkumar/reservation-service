package com.hrs.reservarion.clients.api;

import com.hrs.reservarion.clients.dto.CustomerDto;
import com.hrs.reservarion.clients.dto.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerManagementClient {

    @Autowired
    private WebClient webClient;

    @Value("${gateway-server.baseUrl}")
    private String gateWayServerUrl;

    public CustomerDto getCustomerById(String customerId) {
        return webClient.get()
                .uri(gateWayServerUrl + "/customer-service/api/v1/customer/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block();
    }

}
