package org.quack.QUACKServer.global.util;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double haversineCalculateDistance(double userLatitude, double userLongitude,
                                           double restaurantLatitude, double restaurantLongitude){

        double distanceLatitude = Math.toRadians(restaurantLatitude - userLatitude);
        double distanceLongitude = Math.toRadians(restaurantLongitude - userLongitude);

        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2)
                + Math.cos(Math.toRadians(userLatitude)) * Math.cos(Math.toRadians(restaurantLatitude))
                * Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

}
