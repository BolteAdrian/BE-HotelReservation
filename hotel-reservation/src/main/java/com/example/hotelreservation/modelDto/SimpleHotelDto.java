package com.example.hotelreservation.modelDto;

/**
 * Data Transfer Object (DTO) representing a simplified view of a hotel.
 * This class is used to encapsulate basic hotel details without including
 * additional information such as rooms or feedback.
 */
public class SimpleHotelDto {

    // Unique identifier of the hotel
    private Long id;

    // Name of the hotel
    private String name;

    // Latitude of the hotel's location
    private double latitude;

    // Longitude of the hotel's location
    private double longitude;

    /**
     * Default constructor.
     */
    public SimpleHotelDto() {
    }

    /**
     * Constructs a new instance of SimpleHotelDto with the specified hotel details.
     *
     * @param id the unique identifier of the hotel.
     * @param name the name of the hotel.
     * @param latitude the latitude of the hotel's location.
     * @param longitude the longitude of the hotel's location.
     */
    public SimpleHotelDto(Long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the unique identifier of the hotel.
     *
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the hotel.
     *
     * @param id the unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the hotel.
     *
     * @return the name of the hotel.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the latitude of the hotel's location.
     *
     * @return the latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the hotel's location.
     *
     * @param latitude the latitude to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the hotel's location.
     *
     * @return the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the hotel's location.
     *
     * @param longitude the longitude to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
