package com.example.alarmsystem;

public class Alarm {

    private String time;
    private String name;

    public Alarm(String mTime, String mName){
        time = mTime;
        name = mName;
    }
    public String getTime(){return time;}
    public String getName(){return name;}

}
