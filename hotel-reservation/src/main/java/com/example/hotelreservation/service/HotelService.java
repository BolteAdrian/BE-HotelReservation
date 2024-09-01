package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Feedback;
import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.modelDto.HotelWithRating;
import com.example.hotelreservation.modelDto.SimpleHotelDto;
import com.example.hotelreservation.repository.HotelRepository;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.utlis.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing hotel-related operations, including retrieving hotels, rooms, and calculating ratings.
 */
@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    /**
     * Constructor for HotelService.
     *
     * @param hotelRepository the repository used to manage hotel data.
     * @param roomRepository the repository used to manage room data.
     */
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Retrieves a list of all hotels.
     *
     * @return a list of {@link Hotel} objects.
     */
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * Retrieves a list of available rooms in a specific hotel.
     *
     * @param hotelId the ID of the hotel.
     * @return a list of {@link Room} objects with rooms.
     */
    public List<Room> getRooms(Long hotelId) {
        List<Room> rooms = roomRepository.findAvailableRoomsByHotelId(hotelId);
        return rooms;
    }

    /**
     * Retrieves a list of hotels within a specified radius from a given location, including their average ratings.
     *
     * @param userLat the latitude of the user's location.
     * @param userLon the longitude of the user's location.
     * @param radius the radius within which to search for hotels, in kilometers.
     * @return a list of {@link HotelWithRating} objects representing hotels within the radius and their average ratings.
     */
    public List<HotelWithRating> getHotelsWithinRadius(double userLat, double userLon, double radius) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<HotelWithRating> nearbyHotelsWithRatings = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            double distance = DistanceCalculator.calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude());
            if (distance <= radius) {
                double averageRating = calculateAverageRating(hotel.getFeedbacks());
                SimpleHotelDto hotelDto = new SimpleHotelDto(hotel.getId(), hotel.getName(), hotel.getLatitude(), hotel.getLongitude());
                nearbyHotelsWithRatings.add(new HotelWithRating(hotelDto, averageRating));
            }
        }
        return nearbyHotelsWithRatings;
    }

    /**
     * Calculates the average rating from a list of feedbacks.
     *
     * @param feedbacks the list of {@link Feedback} objects.
     * @return the average rating as a {@code double}. Returns 0.0 if no feedback is available.
     */
    private double calculateAverageRating(List<Feedback> feedbacks) {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Feedback feedback : feedbacks) {
            sum += feedback.getRating();
        }
        return sum / feedbacks.size();
    }
}
