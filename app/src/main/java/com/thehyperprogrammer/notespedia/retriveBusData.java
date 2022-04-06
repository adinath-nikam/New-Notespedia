package com.thehyperprogrammer.notespedia;

public class retriveBusData {

    private String bus_number;
    private Double latitude,longitude;
    public retriveBusData(String bus_number, Double latitude, Double longitude) {
        this.bus_number = bus_number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public retriveBusData() {
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
