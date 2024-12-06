package com.jorimpablico.travelbookingapp.model;

public class BookingRequestModel {

    String name_of_place;
    String location;
    String date;
    int number_of_people;
    String name_of_user;
    int price;

    public BookingRequestModel(String name_of_place, String location, String date, int number_of_people, String name_of_user, int price) {
        this.name_of_place = name_of_place;
        this.location = location;
        this.date = date;
        this.number_of_people = number_of_people;
        this.name_of_user = name_of_user;
        this.price = price;
    }

    public String getName_of_place() {
        return name_of_place;
    }

    public void setName_of_place(String name_of_place) {
        this.name_of_place = name_of_place;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(int number_of_people) {
        this.number_of_people = number_of_people;
    }

    public String getName_of_user() {
        return name_of_user;
    }

    public void setName_of_user(String name_of_user) {
        this.name_of_user = name_of_user;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
