package com.hrs.reservarion.clients.api;

import com.hrs.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PayementManagementClient {

    @Autowired
    private WebClient webClient;

    @Value("${gateway-server.baseUrl}")
    private String gateWayServerUrl;

    public PaymentDto getPaymentInfo(String reservationId) {
        return webClient.get()
                .uri(gateWayServerUrl + "/payment-service/api/v1/payment/reservation/{id}", reservationId)
                .retrieve()
                .bodyToMono(PaymentDto.class)
                .block();
    }

}
