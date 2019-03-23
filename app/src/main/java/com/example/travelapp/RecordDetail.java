package com.example.travelapp;

public class RecordDetail {
    private String photodescrible;
    private int photoid;
    public RecordDetail(String photodescrible,int photoid)
    {
        this.photodescrible=photodescrible;
        this.photoid=photoid;
    }
    public String getPhotodescrible()
    {
        return photodescrible;
    }
    public int getPhotoid()
    {
        return photoid;
    }
}
