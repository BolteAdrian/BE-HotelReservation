package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.utlis.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final List<Hotel> hotels;
    private final RoomRepository roomRepository;

    public HotelService(List<Hotel> hotels, RoomRepository roomRepository) {
        this.hotels = hotels;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotels;
    }

    public List<Room> getRooms(Long hotelId) {
        for (Hotel hotel : hotels) {
            if (hotel.getId().equals(hotelId)) {
                List<Room> availableRooms = new ArrayList<>();
                for (Room room : hotel.getRooms()) {
                        availableRooms.add(room);
                }
                return availableRooms;
            }
        }
        return new ArrayList<>();
    }

    public List<Hotel> getHotelsWithinRadius(double userLat, double userLon, double radius) {
        List<Hotel> nearbyHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            double distance = DistanceCalculator.calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude());
            if (distance <= radius) {
                nearbyHotels.add(hotel);
            }
        }
        return nearbyHotels;
    }
}
