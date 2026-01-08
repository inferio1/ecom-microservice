package com.demo.producer;

public class RiderLocation {
    private String riderId;
    private double latitude;
    private double longitutde;

    public RiderLocation(String riderId, double longitutde, double latitude) {
        this.longitutde = longitutde;
        this.riderId = riderId;
        this.latitude = latitude;
    }

    public String getRiderId() {
        return riderId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitutde() {
        return longitutde;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitutde(double longitutde) {
        this.longitutde = longitutde;
    }

    @Override
    public String toString() {
        return "RiderLocation{" +
                "riderId='" + riderId + '\'' +
                ", latitude=" + latitude +
                ", longitutde=" + longitutde +
                '}';
    }

    public RiderLocation() {
    }
}
