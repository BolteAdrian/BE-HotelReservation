package com.example.hotelreservation.config;

import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.repository.HotelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private HotelRepository hotelRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<Hotel>> typeReference = new TypeReference<List<Hotel>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/hotels.json");
            try {
                List<Hotel> hotels = objectMapper.readValue(inputStream, typeReference);
                for (Hotel hotel : hotels) {
                    for (Room room : hotel.getRooms()) {
                        room.setHotel(hotel);
                    }
                }
                hotelRepository.saveAll(hotels);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

