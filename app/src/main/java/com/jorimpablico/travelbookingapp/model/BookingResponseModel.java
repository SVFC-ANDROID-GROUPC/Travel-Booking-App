package com.jorimpablico.travelbookingapp.model;

public class BookingResponseModel extends BookingRequestModel {

    int user_id;
    int booking_id;
    String booking_uuid;
    String user_uuid;
    String created_at;
    String updated_at;

    public BookingResponseModel(String name_of_place, String location, String date, int number_of_people, String name_of_user, int price) {
        super(name_of_place, location, date, number_of_people, name_of_user, price);
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooking_uuid() {
        return booking_uuid;
    }

    public void setBooking_uuid(String booking_uuid) {
        this.booking_uuid = booking_uuid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
