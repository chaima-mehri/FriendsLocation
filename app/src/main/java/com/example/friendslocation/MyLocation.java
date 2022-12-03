package com.example.friendslocation;

public class MyLocation {
    String nom;
    String numéro;
    String longitude;
    String latitude;

    public MyLocation() {
    }

    public MyLocation(String nom, String numéro, String longitude, String latitude) {
        this.nom = nom;
        this.numéro = numéro;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNuméro() {
        return numéro;
    }

    public void setNuméro(String numéro) {
        this.numéro = numéro;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "nom='" + nom + '\'' +
                ", numéro='" + numéro + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
