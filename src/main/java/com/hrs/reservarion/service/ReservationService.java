package com.hrs.reservarion.service;

import com.hrs.dto.ReservationReqDto;
import com.hrs.reservarion.clients.api.CustomerManagementClient;
import com.hrs.reservarion.clients.api.HotelManagementClient;
import com.hrs.reservarion.clients.api.PayementManagementClient;
import com.hrs.reservarion.clients.dto.RoomDto;
import com.hrs.dto.ReservationRespDto;
import com.hrs.reservarion.exception.ValidationException;
import com.hrs.reservarion.kafka.ProducerService;
import com.hrs.reservarion.model.Reservation;
import com.hrs.reservarion.repostitory.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private HotelManagementClient hotelManagementClient;
	@Autowired
	private CustomerManagementClient customerManagementClient;

	@Autowired
	private PayementManagementClient payementManagementClient;

	public ReservationReqDto createReservation(ReservationReqDto reqDto) {

		//Fetch the HotelInformation
		var hotelDto = hotelManagementClient.getHotelById(reqDto.getHotelId());
		//Filter the hotelRooms with given roomType
		var registeredRooms = hotelDto.getRooms().stream()
				.filter(roomDto -> roomDto.getRoomType().equalsIgnoreCase(reqDto.getRoomType()))
				.toList();
		Reservation reservation = null;
		if (registeredRooms.isEmpty()) {
			throw new ValidationException("No Rooms Available with the given RoomType");
		}

		for (RoomDto room : registeredRooms) {
			var roomNumber = room.getRoomNumber();
			if (findExistingReservations(reqDto, roomNumber).isEmpty()) {
				reservation = saveReservation(reqDto, roomNumber);
				log.info("Reservation Saved Successfully");

				reqDto.setReservationId(reservation.getReservationId());
				reqDto.setAmount(room.getPrice());

				producerService.publishReservationCreatedEvent(reqDto);
				//send to Payment Gateway
				//Write the listener to update the reservation to Reserved
				break;
			}
		}

		if (reservation == null) {
			throw new ValidationException("No Available Rooms found within given dates");
		}
		return modelMapper.map(reservation, ReservationReqDto.class);
	}

	private Reservation saveReservation(ReservationReqDto reqdto, String roomNumber) {

		Reservation reservation = modelMapper.map(reqdto, Reservation.class);
		reservation.setReservationId(UUID.randomUUID().toString());
		reservation.setRoomNumber(roomNumber);
		reservation.setStatus("Created");
		reservation.setExpiryDate(ZonedDateTime.now().plusMinutes(10));
		return reservationRepository.save(reservation);
	}

	private List<Reservation> findExistingReservations(ReservationReqDto reqdto, String roomNumber){
		return reservationRepository
				.findOverlappingReservations(reqdto.getHotelId(),roomNumber,reqdto.getStartDate(),reqdto.getEndDate())
				.stream()
				.filter(reservation -> reservation.getStatus().equalsIgnoreCase("Reserved"))
				.toList();
	}


	public ReservationRespDto getReservationsById(String reservationId) {
		var reservationOpt = reservationRepository.findById(reservationId);
		if (reservationOpt.isEmpty()) {
			throw new ValidationException("Customer is not Present");
		}

		var reservation = reservationOpt.get();
		var reservationResp= modelMapper.map(reservation, ReservationRespDto.class);
		reservationResp.setStartDate(reservation.getStartDate());
		reservationResp.setEndDate(reservation.getEndDate());

		//Fetch Customer Details
		//Fetch Hotel Details

		var customer = customerManagementClient.getCustomerById(reservation.getCustomerId());
		var hotelDetails = hotelManagementClient.getHotelById(reservation.getHotelId());
		var paymentInfo = payementManagementClient.getPaymentInfo(reservation.getReservationId());
		var roomInformation = hotelDetails.getRooms().stream().filter(roomDto -> roomDto.getRoomNumber().equalsIgnoreCase(reservation.getRoomNumber())).findFirst().orElse(null);
		hotelDetails.setRooms(null);

		reservationResp.setCustomer(customer);
		reservationResp.setHotel(hotelDetails);
		reservationResp.setRoom(roomInformation);
		reservationResp.setPayment(paymentInfo);
		return reservationResp;
	}

	public List<ReservationRespDto> getReservationsByHotel(Long hotelId) {
		var reservations = reservationRepository.findByHotelId(hotelId);
		return reservations.stream().map(reservation -> {
			var resp = getReservationsById(reservation.getReservationId());
			resp.setHotel(null);
			return resp;
		}).toList();

	}

	public List<ReservationRespDto> getReservationsByCustomer(String customerId) {
		var reservations = reservationRepository.findByCustomerId(customerId);
		return reservations.stream().map(reservation -> {
			var resp = getReservationsById(reservation.getReservationId());
			resp.setCustomer(null);
			return resp;
		}).toList();
	}
}