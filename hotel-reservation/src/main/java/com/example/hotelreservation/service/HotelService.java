package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.repository.HotelRepository;
import com.example.hotelreservation.repository.RoomRepository;
import com.example.hotelreservation.utlis.DistanceCalculator;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Room> getRooms(Long hotelId) {
        return roomRepository.findAvailableRoomsByHotelId(hotelId);
    }

    public List<Hotel> getHotelsWithinRadius(double userLat, double userLon, double radius) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> nearbyHotels = new ArrayList<>();
        for (Hotel hotel : allHotels) {
            double distance = DistanceCalculator.calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude());
            if (distance <= radius) {
                nearbyHotels.add(hotel);
            }
        }
        return nearbyHotels;
    }
}
