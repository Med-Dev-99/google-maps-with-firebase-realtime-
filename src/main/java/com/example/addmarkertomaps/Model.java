package com.example.addmarkertomaps;

public class Model {
    public Model(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    double longtitude;
    double latitude;

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public Model() {
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
