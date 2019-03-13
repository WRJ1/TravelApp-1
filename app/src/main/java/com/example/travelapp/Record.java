package com.example.travelapp;

public class Record {
    private String date;
    private String location;

    public Record(String date,String location){
        this.date = date;
        this.location = location;
    }

    public String getDate(){
        return date;
    }

    public String getLocation(){
        return location;
    }

}
