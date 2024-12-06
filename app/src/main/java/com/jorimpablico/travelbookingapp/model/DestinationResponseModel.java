package com.jorimpablico.travelbookingapp.model;

public class DestinationResponseModel extends DestinationRequestModel{

    int destination_id;
    String destination_uuid;
    String created_at;
    String updated_at;

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public String getDestination_uuid() {
        return destination_uuid;
    }

    public void setDestination_uuid(String destination_uuid) {
        this.destination_uuid = destination_uuid;
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
