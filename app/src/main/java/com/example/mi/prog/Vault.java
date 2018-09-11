package com.example.mi.prog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class Vault { //TODO: REWORK THIS SHIIIIIT
    public static List<String> toCheerUp  = new ArrayList<>(),
            writeToWhenBored = new ArrayList<>(),
            toDoToAchieveYourGoals = new ArrayList<>(),
            extraActivities = new ArrayList<>();
    public static String prefferedPlaces, musicWithGoodMood,musicWithBadMood, Name;
    public List<String> toCheerUpD  = new ArrayList<>(),
            writeToWhenBoredD = new ArrayList<>(),
            toDoToAchieveYourGoalsD = new ArrayList<>(),
            extraActivitiesD = new ArrayList<>();
    public String prefferedPlacesD,musicWithGoodMoodD,musicWithBadMoodD, NameD;
    int hourWorkSD,minuteWorkSD,hourWorkED,minuteWorkED,hourRestSD,minuteRestSD,hourRestED,minuteRestED;
    static int hourWorkS,minuteWorkS,hourWorkE,minuteWorkE,hourRestS,minuteRestS,hourRestE,minuteRestE;
    public void jsonHandicapWrite()
    {
        toCheerUpD = toCheerUp;
        writeToWhenBoredD = writeToWhenBored;
        prefferedPlacesD = prefferedPlaces;
        extraActivitiesD = extraActivities;
        musicWithBadMoodD = musicWithBadMood;
        musicWithGoodMoodD = musicWithGoodMood;
        toDoToAchieveYourGoalsD=toDoToAchieveYourGoals;
        NameD = Name;
        hourRestED = hourRestE;
        hourRestSD = hourRestS;
        hourWorkED = hourWorkE;
        hourWorkSD = hourWorkS;
        minuteRestED = minuteRestE;
        minuteRestSD = minuteRestS;
        minuteWorkED = minuteWorkE;
        minuteWorkSD = minuteWorkS;
    }
    public void jsonHandicapRead()
    {
        toCheerUp = toCheerUpD;
        writeToWhenBored = writeToWhenBoredD;
        prefferedPlaces = prefferedPlacesD;
        extraActivities = extraActivitiesD;
        musicWithBadMood = musicWithBadMoodD;
        musicWithGoodMood = musicWithGoodMoodD;
        toDoToAchieveYourGoals=toDoToAchieveYourGoalsD;
        Name=NameD;
        hourRestE = hourRestED;
        hourRestS = hourRestSD;
        hourWorkE = hourWorkED;
        hourWorkS = hourWorkSD;
        minuteRestE = minuteRestED;
        minuteRestS = minuteRestSD;
        minuteWorkE = minuteWorkED;
        minuteWorkS = minuteWorkSD;
    }
    public void startAlarmTime(Vault V,Context context) {
        try {
            AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            Intent intent;
            long time;
            PendingIntent pendingIntent;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Calendar c = Calendar.getInstance();
            String dateStr = c.get(Calendar.YEAR)+"-"; //" "+
            if((c.get(Calendar.MONTH)+1)<=9)
                dateStr = dateStr + "0" + (c.get(Calendar.MONTH)+1)+"-";
            else
                dateStr = dateStr + (c.get(Calendar.MONTH)+1)+"-";
            if(c.get(Calendar.DAY_OF_MONTH)<=9)
                dateStr = dateStr + "0" + c.get(Calendar.DAY_OF_MONTH);
            else
                dateStr = dateStr + c.get(Calendar.DAY_OF_MONTH);
            dateStr = dateStr + " ";

            String dateStrEnd;

            //WS
            if(V.hourWorkS<=9)
                dateStrEnd = "0"+V.hourWorkS+":";
            else
                dateStrEnd = V.hourWorkS+":";
            if(V.minuteWorkS<=9)
                dateStrEnd = dateStrEnd+"0"+V.minuteWorkS+":00.000";
            else
                dateStrEnd = dateStrEnd+V.minuteWorkS+":00.000";

            Date dateWS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWS.toString());
            intent.putExtra("title","Пора за работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourWorkS*100 +V.minuteWorkS);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourWorkS*100 +V.minuteWorkS, intent, FLAG_UPDATE_CURRENT);
            time = dateWS.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //WE
            if(V.hourWorkE<=9)
                dateStrEnd = "0"+V.hourWorkE+":";
            else
                dateStrEnd = V.hourWorkE+":";
            if(V.minuteWorkE<=9)
                dateStrEnd = dateStrEnd+"0"+V.minuteWorkE+":00.000";
            else
                dateStrEnd = dateStrEnd+V.minuteWorkE+":00.000";

            Date dateWE = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWE.toString());
            intent.putExtra("title","Пора закончить работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourWorkE*100 +V.minuteWorkE);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourWorkE*100 +V.minuteWorkE, intent, FLAG_UPDATE_CURRENT);
            time = dateWE.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RS
            if(V.hourRestS<=9)
                dateStrEnd = "0"+V.hourRestS+":";
            else
                dateStrEnd = V.hourRestS+":";
            if(V.minuteRestS<=9)
                dateStrEnd = dateStrEnd+"0"+V.minuteRestS+":00.000";
            else
                dateStrEnd = dateStrEnd+V.minuteRestS+":00.000";
            Date dateRS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateRS.toString());
            intent.putExtra("title","Пора отдыхать");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourRestS*100 +V.minuteRestS);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourRestS*100 +V.minuteRestS, intent, FLAG_UPDATE_CURRENT);
            time = dateRS.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RE:ZERO
            if(V.hourRestE<=9)
                dateStrEnd = "0"+V.hourRestE+":";
            else
                dateStrEnd = V.hourRestE+":";
            if(V.minuteRestE<=9)
                dateStrEnd = dateStrEnd+"0"+V.minuteRestE+":00.000";
            else
                dateStrEnd = dateStrEnd+V.minuteRestE+":00.000";
            Date dateRE = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateRE.toString());
            intent.putExtra("title","Пора перестать отдыхать");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourRestE*100 +V.minuteRestE);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.hourRestE*100 +V.minuteRestE, intent, FLAG_UPDATE_CURRENT);
            time = dateRE.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RestRandom
            if(dateRE.getTime()>dateRS.getTime()) {
                long timeRand = ThreadLocalRandom.current().nextLong(dateRS.getTime(), dateRE.getTime());
                //Randomising the text on notification
                int whatToSay = ThreadLocalRandom.current().nextInt(0,5);
                Log.i(TAG,"WhatToSay= " + whatToSay);
                int i;
                whatToSay =3;
                switch (whatToSay)
                {
                    case 0://toCheerUp
                        intent = new Intent(context, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,V.toCheerUp.size());
                        intent.putExtra("text",V.toCheerUp.get(i));
                        intent.putExtra("title", "Тебе грустно?");
                        intent.putExtra("ID", 1);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 1://writeToWhenNored
                        intent = new Intent(context, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,V.writeToWhenBored.size());
                        intent.putExtra("text",V.writeToWhenBored.get(i));
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 2);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 2://toDoToAchieveYourGoals
                        intent = new Intent(context, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,V.toDoToAchieveYourGoals.size());
                        intent.putExtra("text","Займись этим: "+V.toDoToAchieveYourGoals.get(i));
                        intent.putExtra("title", "Нечего делать? Время достигать свои цели!");
                        intent.putExtra("ID", 3);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 3://prefferedplaces
                        intent = new Intent(context, AlarmNotificationReceiver.class);
                        intent.putExtra("specialIntent","places");
                        intent.putExtra("text","Не сходить ли тебе сюда: "+V.prefferedPlacesD);
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 4);
                        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 4:
                        intent = new Intent(context, AlarmNotificationReceiver.class);
                        intent.putExtra("specialIntent","question");
                        intent.putExtra("text"," ");
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 5);
                        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                }
            }

        } catch (Exception e) {
            Log.i(TAG, "EXCEPTION FOUND IN START ALARM TIME:    "+e);
        }
    }
    public String TimeNormaliser(int hourOfDay,int minuteP)
    {
        if(hourOfDay<10&&minuteP<10)
            return("0"+hourOfDay+" : 0"+minuteP);
        else {
            if (hourOfDay < 10)
                return("0" + hourOfDay + " : " + minuteP);
            else
            if (minuteP < 10)
                return (hourOfDay + " : 0" + minuteP);
            else
                return(hourOfDay+" : "+minuteP);
        }
    }
}
