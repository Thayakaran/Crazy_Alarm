package com.example.crazyalarm;

public class Alarm {

    private String time;
    private String name;
    private String count;
    private String id;
    private String tone;
    private String status;

    public Alarm(String mTime, String mName, String mId, String mTone,String mCount,String mStatus){
        time = mTime;
        name = mName;
        id = mId;
        tone = mTone;
        count = mCount;
        status = mStatus;
    }
    public String getTime(){return time;}
    public String getName(){return name;}
    public String getId(){return id;}
    public String getTone(){return tone;}
    public String getCount(){return count;}
    public String getStatus(){return status;}

}
// Thaya updated new branch