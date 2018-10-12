package ch.heigvd.amt.wp1.model;

public class Adress {
    private final int number;
    private final String street;
    private final int zipCode;
    private final String city;
    private final String country;

    public Adress(int number, String street, int zipCode, String city, String country) {
        this.number = number;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public int getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

