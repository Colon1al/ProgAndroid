package com.example.mi.prog;

import java.io.Serializable;

public class EventClass implements Serializable
{
    private String Name, Info;
   private int year,month,day,type,hours,minutes,repeat,hoursE,minutesE;
   //repeat: {0 :no rep},{1:every day bro with the DISNEY CHANNEL FLOOOOOOW},
   //{2:EVERY WEEK},{3:Every month},{4:Every year};



    public EventClass(String Name, String info,int year,int month,int day,int hours, int minutes,int repeat)//TODO:GOVNOCODE?????
    {
        this.Name = Name;
        this.Info = info;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.repeat = repeat;
        this.minutesE = minutesE;
        this.hoursE = hoursE;


    }
    //getters and setters
    //Name
    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }
    //Info
    public String getInfo()
    {
        return Info;
    }

    public void setInfo(String info)
    {
        Info = info;
    }
    //Day
    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }
    //Year
    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
    //Month
    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }
    //Time
    public String getTime()
    {
        if(hours<10 && minutes<10)
        {return "0"+hours + " : 0" + minutes;}
        if(hours<10)
        {   return "0"+hours + " : " + minutes;}
        if(minutes<10)
        {   return hours + " : 0"+minutes;}
        return hours + " : "+minutes;

    }
    public String getTimeE()
    {
        if(hoursE<10 && minutesE<10)
        {return "0"+hoursE + " : 0" + minutesE;}
        if(hoursE<10)
        {   return "0"+hoursE + " : " + minutesE;}
        if(minutesE<10)
        {   return hoursE + " : 0"+minutesE;}
        return hoursE + " : "+minutesE;

    }

    public int getRepeat() {
        return repeat;
    }
    public boolean isEqual(EventClass E)//TODO: OVERLOAD OPERATOR IF POSSIBLE
    {
        if(E.getName().equals(Name) && E.getHours()==hours && E.getMinutes()==minutes&&E.getYear()==year&&E.getMonth()==month&&E.getDay()==day&&E.getRepeat()==repeat){return true;} else return false;
    }
    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}