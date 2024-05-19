package com.example.hotelreservation.config;

import com.example.hotelreservation.model.Hotel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class DataConfig {

    @Bean
    public List<Hotel> hotels() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Hotel>> typeReference = new TypeReference<List<Hotel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/hotels.json");
        return objectMapper.readValue(inputStream, typeReference);
    }
}