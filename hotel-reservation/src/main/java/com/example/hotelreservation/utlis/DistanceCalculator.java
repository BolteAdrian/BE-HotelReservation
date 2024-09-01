package com.example.hotelreservation.utlis;

/**
 * Utility class for calculating the distance between two geographical points.
 */
public class DistanceCalculator {

    /**
     * Calculates the distance between two points specified by their latitude and longitude.
     *
     * This method uses the Haversine formula to compute the distance between two points on the Earth's surface.
     *
     * @param lat1 the latitude of the first point in decimal degrees.
     * @param lon1 the longitude of the first point in decimal degrees.
     * @param lat2 the latitude of the second point in decimal degrees.
     * @param lon2 the longitude of the second point in decimal degrees.
     * @return the distance between the two points in kilometers.
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in kilometers

        // Convert latitude and longitude from degrees to radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Haversine formula to calculate the distance
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        // Calculate the angular distance in radians
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance in kilometers
        double distance = R * c;

        return distance;
    }
}
