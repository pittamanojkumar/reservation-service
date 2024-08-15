package com.hrs.reservarion.kafka;

import com.hrs.dto.ReservationReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReservationCreatedEvent(ReservationReqDto reqDto)
    {
        log.info("publish Reservation created Event {}",reqDto);
        this.kafkaTemplate.send("reservation-created", reqDto);
        log.info("Message Published");
    }
}
