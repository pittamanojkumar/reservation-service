package com.hrs.reservarion.repostitory;

import com.hrs.reservarion.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    @Query("SELECT r FROM Reservation r WHERE r.hotelId = :hotelId AND r.roomNumber = :roomNumber " +
            "AND (r.startDate < :endDate AND r.endDate > :startDate)")
    List<Reservation> findOverlappingReservations(
            @Param("hotelId") Long hotelId,
            @Param("roomNumber") String roomNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    List<Reservation> findByCustomerId(String customerId);

    List<Reservation> findByHotelId(Long hotelId);
}