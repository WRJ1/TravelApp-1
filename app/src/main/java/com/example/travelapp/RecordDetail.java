package com.example.travelapp;

public class RecordDetail {
    private String photodescrible;
    //private int photoid;
    private String photoid;

    public RecordDetail(String photodescrible,String photoid)
    {
        this.photodescrible=photodescrible;
        this.photoid=photoid;
    }
    public String getPhotodescrible()
    {
        return photodescrible;
    }

    public String getPhotoid()
    {
        return photoid;
    }
}
