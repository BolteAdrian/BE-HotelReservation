package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Reservation bookRoom(Long userId, Long roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent() && room.get().isAvailable()) {
            Reservation reservation = new Reservation();
            reservation.setUserId(userId);
            reservation.setRoomId(roomId);
            reservation.setCheckInTime(checkIn);
            reservation.setCheckOutTime(checkOut);
            reservation.setRoom(room.get());

            // Update room availability
            room.get().setAvailable(false);
            roomRepository.save(room.get());

            return reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Room is not available");
        }
    }

    public String cancelReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent() && reservation.get().getCheckInTime().isAfter(LocalDateTime.now().plusHours(2))) {
            // Mark room as available
            Room room = reservation.get().getRoom();
            room.setAvailable(true);
            roomRepository.save(room);

            reservationRepository.deleteById(reservationId);
            return "Reservation cancelled successfully";
        } else {
            throw new IllegalStateException("Reservation cannot be cancelled within two hours of check-in");
        }
    }

    public Reservation changeReservation(Long reservationId, Long newRoomId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Optional<Room> newRoom = roomRepository.findById(newRoomId);
        if (reservation.isPresent() && newRoom.isPresent() && newRoom.get().isAvailable()) {
            // Mark old room as available
            Room oldRoom = reservation.get().getRoom();
            oldRoom.setAvailable(true);
            roomRepository.save(oldRoom);

            // Update reservation with new room
            Reservation updatedReservation = reservation.get();
            updatedReservation.setRoomId(newRoomId);
            updatedReservation.setRoom(newRoom.get());

            // Mark new room as unavailable
            newRoom.get().setAvailable(false);
            roomRepository.save(newRoom.get());

            return reservationRepository.save(updatedReservation);
        } else {
            throw new IllegalStateException("New room is not available");
        }
    }
}
