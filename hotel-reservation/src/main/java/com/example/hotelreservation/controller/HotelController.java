package com.example.hotelreservation.controller;

import com.example.hotelreservation.model.Feedback;
import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.service.FeedbackService;
import com.example.hotelreservation.service.HotelService;
import com.example.hotelreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@CrossOrigin(origins = "http://localhost:4200") // Adnotare CORS
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/withinRadius")
    public List<Hotel> getHotelsWithinRadius(@RequestParam double userLat, @RequestParam double userLon, @RequestParam double radius) {
        return hotelService.getHotelsWithinRadius(userLat, userLon, radius);
    }

    @GetMapping("/{hotelId}/rooms")
    public List<Room> getRooms(@PathVariable Long hotelId) {
        return hotelService.getRooms(hotelId);
    }

    @PostMapping("/book")
    public Reservation bookRoom(@RequestParam Long userId, @RequestParam Long roomId, @RequestParam LocalDateTime checkIn, @RequestParam LocalDateTime checkOut) {
        return reservationService.bookRoom(userId, roomId, checkIn, checkOut);
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    @PostMapping("/change")
    public Reservation changeReservation(@RequestParam Long reservationId, @RequestParam Long newRoomId) {
        return reservationService.changeReservation(reservationId, newRoomId);
    }

    @PostMapping("/{hotelId}/feedback")
    public Feedback leaveFeedback(@PathVariable Long hotelId, @RequestParam Long userId, @RequestParam String comment, @RequestParam int rating) {
        return feedbackService.leaveFeedback(hotelId, userId, comment, rating);
    }
}

