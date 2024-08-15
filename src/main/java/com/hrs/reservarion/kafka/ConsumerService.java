package com.hrs.reservarion.kafka;

import com.hrs.dto.PaymentDto;
import com.hrs.reservarion.repostitory.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private ReservationRepository reservationRepository;

    @KafkaListener(topics = "payment-completed",
            groupId = "group-id")
    public void consumePaymentCompletedEvent(PaymentDto paymentDto) {
        log.info("Received Payment Completion Event {}", paymentDto);

        var reservationOpt = reservationRepository.findById(paymentDto.getReservationId());
        if (reservationOpt.isPresent()) {
            var reservation = reservationOpt.get();
            reservation.setStatus("Reserved");
            log.info("Reservation Completed {}",reservation.getReservationId());
            reservationRepository.save(reservation);

        }
    }
}
