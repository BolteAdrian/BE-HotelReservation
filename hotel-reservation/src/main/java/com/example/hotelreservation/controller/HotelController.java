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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * @return a list of {@link HotelWithRating} objects within the specified radius.
     */
    @GetMapping("/withinRadius")
    public List<HotelWithRating> getHotelsWithinRadius(@RequestParam double userLat, @RequestParam double userLon, @RequestParam double radius) {
        return hotelService.getHotelsWithinRadius(userLat, userLon, radius);
    }

    /**
     * Retrieves a list of rooms available in a specific hotel.
     *
     * @param hotelId the ID of the hotel.
     * @return a list of {@link Room} objects available in the specified hotel.
     */
    @GetMapping("/{hotelId}/rooms")
    public List<Room> getAvailableRooms(@PathVariable Long hotelId) {
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
     * Changes an existing reservation for a given user in a specific hotel.
     *
     * @param changeReservationDto the DTO containing user ID, hotel ID, and the new room ID.
     * @return the updated {@link Reservation} object after the change.
     */
    @PostMapping("/change")
    public Reservation changeReservation(@RequestBody ChangeReservationDto changeReservationDto) {
        return reservationService.changeReservation(
                changeReservationDto.getUserId(),
                changeReservationDto.getHotelId(),
                changeReservationDto.getNewRoomId()
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
    public Feedback submitFeedback(@PathVariable Long hotelId, @RequestBody FeedbackDto feedbackDto) {
        return feedbackService.leaveFeedback(
                hotelId,
                feedbackDto.getUserId(),
                feedbackDto.getComment(),
                feedbackDto.getRating()
        );
    }

    /**
     * Retrieves the details of a hotel including available rooms, feedback, and reservation status for a specific user.
     *
     * This method fetches the details of a hotel based on the hotel ID and optionally, the user's reservation status.
     * It also allows filtering available rooms based on a specified date range.
     *
     * @param hotelId the ID of the hotel for which details are being requested.
     * @param userId the ID of the user to check for any existing reservations.
     * @param startDate optional start date for filtering available rooms. If not provided, defaults to the current date.
     * @param endDate optional end date for filtering available rooms. If not provided, defaults to one day after the start date.
     * @return a {@link HotelDetailsDto} object containing details about the hotel, including available rooms and feedback.
     */
    @GetMapping("/{hotelId}/details")
    public HotelDetailsDto getHotelDetails(
            @PathVariable Long hotelId,
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        // Convert strings to LocalDate
        LocalDate startLocalDate = startDate != null ? LocalDate.parse(startDate) : LocalDate.now();
        LocalDate endLocalDate = endDate != null ? LocalDate.parse(endDate) : startLocalDate.plusDays(1);

        // Convert LocalDate to LocalDateTime with default time
        LocalDateTime start = startLocalDate.atStartOfDay();
        LocalDateTime end = endLocalDate.atStartOfDay().plusDays(1); // Consider end date as inclusive

        // Retrieve available rooms based on the specified date range
        List<Room> rooms = reservationService.getAvailableRooms(hotelId, start, end);

        // Fetch feedback for the hotel
        List<FeedbackDto> feedbacks = feedbackService.getFeedbacks(hotelId);

        // Check if the user has a reservation at the hotel
        boolean hasReservation = reservationService.userHasReservation(userId, hotelId);

        // Create and populate a DTO with hotel details
        HotelDetailsDto hotelDetailsDto = new HotelDetailsDto();
        hotelDetailsDto.setRooms(rooms);
        hotelDetailsDto.setFeedbacks(feedbacks);
        hotelDetailsDto.setHasReservation(hasReservation);

        return hotelDetailsDto;
    }

    /**
     * Handles the check-out process for a specific room in a hotel.
     *
     * This endpoint is used to process the check-out of a room identified by the given room ID.
     * It updates the room's availability status and removes any existing reservations for the room.
     *
     * @param hotelId the ID of the hotel where the room is located.
     * @param roomId the ID of the room that is being checked out.
     * @return a {@link ResponseEntity} with HTTP status 200 OK if the operation is successful.
     */
    @PutMapping("/{hotelId}/rooms/{roomId}/check-out")
    public ResponseEntity<Void> checkOut(@PathVariable Long hotelId, @PathVariable Long roomId) {
        // Perform the check-out operation
        reservationService.checkOut(roomId);
        // Return HTTP 200 OK response
        return ResponseEntity.ok().build();
    }
}
