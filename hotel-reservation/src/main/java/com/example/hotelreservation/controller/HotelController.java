package com.example.hotelreservation.controller;

import com.example.hotelreservation.model.Feedback;
import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.modelDto.*;
import com.example.hotelreservation.service.FeedbackService;
import com.example.hotelreservation.service.HotelService;
import com.example.hotelreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for handling hotel-related operations.
 */
@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of {@link Hotel} objects.
     */
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    /**
     * Retrieves a list of hotels within a specified radius from the user's location.
     *
     * @param userLat the latitude of the user's location.
     * @param userLon the longitude of the user's location.
     * @param radius  the radius within which to search for hotels.
     * @return a list of {@link Hotel} objects within the specified radius.
     */
    @GetMapping("/withinRadius")
    public List<Hotel> getHotelsWithinRadius(@RequestParam double userLat, @RequestParam double userLon, @RequestParam double radius) {
        return hotelService.getHotelsWithinRadius(userLat, userLon, radius);
    }

    /**
     * Retrieves a list of rooms available in a specific hotel.
     *
     * @param hotelId the ID of the hotel.
     * @return a list of {@link Room} objects available in the specified hotel.
     */
    @GetMapping("/{hotelId}/rooms")
    public List<Room> getRooms(@PathVariable Long hotelId) {
        return hotelService.getRooms(hotelId);
    }

    /**
     * Books a room in a specified hotel for a user.
     *
     * @param reservationDto the reservation details including user ID, room ID, check-in, and check-out dates.
     * @return a {@link Reservation} object representing the booked room.
     */
    @PostMapping("/book")
    public Reservation bookRoom(@RequestBody ReservationDto reservationDto) {
        return reservationService.bookRoom(
                reservationDto.getUserId(),
                reservationDto.getRoomId(),
                reservationDto.getCheckIn(),
                reservationDto.getCheckOut()
        );
    }

    /**
     * Submits feedback for a specific hotel.
     *
     * @param hotelId      the ID of the hotel.
     * @param feedbackDto  the feedback details including user ID, comment, and rating.
     * @return a {@link Feedback} object representing the submitted feedback.
     */
    @PostMapping("/{hotelId}/feedback")
    public Feedback leaveFeedback(@PathVariable Long hotelId, @RequestBody FeedbackDto feedbackDto) {
        return feedbackService.leaveFeedback(
                hotelId,
                feedbackDto.getUserId(),
                feedbackDto.getComment(),
                feedbackDto.getRating()
        );
    }

    /**
     * Retrieves a list of rooms and feedbacks for a specific hotel.
     *
     * @param hotelId the ID of the hotel.
     * @return a {@link HotelDetailsDto} object containing a list of rooms and feedbacks for the specified hotel.
     */
    @GetMapping("/{hotelId}/details")
    public HotelDetailsDto getRoomsAndFeedback(@PathVariable Long hotelId) {
        List<Room> rooms = hotelService.getRooms(hotelId);
        List<Feedback> feedbacks = feedbackService.getFeedbacks(hotelId);

        HotelDetailsDto hotelDetailsDto = new HotelDetailsDto();
        hotelDetailsDto.setRooms(rooms);
        hotelDetailsDto.setFeedbacks(feedbacks);

        return hotelDetailsDto;
    }
}
