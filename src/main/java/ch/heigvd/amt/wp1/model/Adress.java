package ch.heigvd.amt.wp1.model;

public class Adress {
    private final int id;
    private int number;
    private String street;
    private int zipCode;
    private String city;
    private String country;

    public Adress(int id, int number, String street, int zipCode, String city, String country) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public int getId() { return id; }

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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

