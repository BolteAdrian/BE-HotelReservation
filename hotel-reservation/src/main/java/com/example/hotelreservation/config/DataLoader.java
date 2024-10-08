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

    /**
     * Bean that loads data from a JSON file when the application starts.
     * The data is mapped to Hotel objects and saved to the database.
     *
     * @return CommandLineRunner that runs at application startup.
     */
    @Bean
    public CommandLineRunner loadInitialHotelData() {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<Hotel>> hotelListTypeReference = new TypeReference<List<Hotel>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/hotels.json");

            try {
                // Read the data from the JSON file and map it to Hotel objects
                List<Hotel> hotels = objectMapper.readValue(inputStream, hotelListTypeReference);

                // Set the associated Hotel for each Room in the list
                for (Hotel hotel : hotels) {
                    for (Room room : hotel.getRooms()) {
                        room.setHotel(hotel);
                    }
                }

                // Save all Hotel objects to the database
                hotelRepository.saveAll(hotels);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
