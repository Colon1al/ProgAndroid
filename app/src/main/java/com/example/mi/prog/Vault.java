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

public class Vault {
    public List<String> toCheerUpD  = new ArrayList<>(),
            writeToWhenBoredD = new ArrayList<>(),
            toDoToAchieveYourGoalsD = new ArrayList<>(),
            extraActivitiesD = new ArrayList<>();
    public String prefferedPlacesD,musicWithGoodMoodD,musicWithBadMoodD, NameD;
    int hourWorkSD,minuteWorkSD,hourWorkED,minuteWorkED,hourRestSD,minuteRestSD,hourRestED,minuteRestED;

   private Date NiceAlarm(String title, String Special,SimpleDateFormat sdf,Context context,AlarmManager am,String dateStr, Calendar c,int hours,int mins)
   {
       try{
           String dateStrEnd;
           if(hours<=9)
                dateStrEnd = "0"+hours+":";
            else
                dateStrEnd = hours+":";
           if(mins<=9)
               dateStrEnd = dateStrEnd+"0"+mins+":00.000";
           else
               dateStrEnd = dateStrEnd+mins+":00.000";
           Date date = sdf.parse(dateStr + dateStrEnd);
           Log.i("Vault", "dateWS Nice Alarm "+ date);
           Intent intent = new Intent(context, AlarmNotificationReceiver.class);
           intent.putExtra("time",date.toString());
           intent.putExtra("title",title);
           intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+hours*100 +mins);
           intent.putExtra("specialIntent",Special);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+hours*100 +mins, intent, FLAG_UPDATE_CURRENT);
           long time;
           time = date.getTime();
           //Non-repeating alarm
           if(time>c.getTimeInMillis())
           {
               Log.i("Vault", "Alarm is ready to be set");
               am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
               Log.i("Vault", "Alarm is set");
           }

           return date;
       } catch (Exception e){return null;}}

    private void AskUser(Context context,String text,String title,int id,String Special,AlarmManager am,long timeRand)
    {
        Log.i("Vault","AskUser started");
        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra("text",text);
        intent.putExtra("title", title);
        intent.putExtra("ID", id);
        intent.putExtra("specialIntent",Special);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
        //Non-repeating alarm
        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
    }


    public void startAlarmTime(StaticVault V,Context context) {
        try {
            Log.i(TAG,"!!!!!!!! START ALARM TIME STARTED");
            AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Calendar c = Calendar.getInstance();
            Log.i("Vault","Calendar Get date "+c.getTime());
            String dateStr = c.get(Calendar.YEAR)+"-";
            if((c.get(Calendar.MONTH)+1)<=9)
                dateStr = dateStr + "0" + (c.get(Calendar.MONTH)+1)+"-";
            else
                dateStr = dateStr + (c.get(Calendar.MONTH)+1)+"-";
            if(c.get(Calendar.DAY_OF_MONTH)<=9)
                dateStr = dateStr + "0" + c.get(Calendar.DAY_OF_MONTH);
            else
                dateStr = dateStr + c.get(Calendar.DAY_OF_MONTH);
            dateStr = dateStr + " ";
            Date dateRS,dateRE,dateWS,dateWE;
            Log.i("Vault", "Before Nice Alarm func");
            dateWS = NiceAlarm("Пора за работу","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourWorkSD,V.GetSVault().minuteWorkSD);
            dateWE = NiceAlarm("Работа закончена, ура!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourWorkED,V.GetSVault().minuteWorkED);
            dateRS = NiceAlarm("Время для отдыха!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourRestSD,V.GetSVault().minuteRestSD);
            dateRE = NiceAlarm("Пора заканчивать отдых!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourRestED,V.GetSVault().minuteRestED);


            //RestRandom
            if(dateRE.getTime()>dateRS.getTime()) {
                long timeRand = ThreadLocalRandom.current().nextLong(dateRS.getTime(), dateRE.getTime());
                Log.i("Vault","dateRS.getTime "+ dateRS.getTime());
                //Randomising the text on notification
                int whatToSay = ThreadLocalRandom.current().nextInt(0,5);
                Log.i("Vault","WhatToSay= " + whatToSay);
                int i;
                switch (whatToSay)
                {
                    case 0://toCheerUp
                        i = ThreadLocalRandom.current().nextInt(0,V.GetSVault().toCheerUpD.size());
                        AskUser(context,V.GetSVault().toCheerUpD.get(i),"Тебе скучно?",1,"Basic",am,timeRand);
                        break;

                    case 1://writeToWhenNore
                        i = ThreadLocalRandom.current().nextInt(0,V.GetSVault().writeToWhenBoredD.size());
                        AskUser(context,V.GetSVault().writeToWhenBoredD.get(i),"Тебе скучно?",2,"Basic",am,timeRand);
                        break;

                    case 2://toDoToAchieveYourGoals
                        i = ThreadLocalRandom.current().nextInt(0,V.GetSVault().toDoToAchieveYourGoalsD.size());
                        AskUser(context,V.GetSVault().toDoToAchieveYourGoalsD.get(i),"Тебе скучно?",3,"Basic",am,timeRand);
                        break;

                    case 3://prefferedplaces
                        AskUser(context,"Не сходить ли тебе сюда: "+V.GetSVault().prefferedPlacesD,"Тебе скучно?",4,"Places",am,timeRand);
                        break;

                    case 4:
                        AskUser(context,"Нажми на меня","Тебе скучно?",5,"question",am,timeRand);
                        break;

                }
            }

        } catch (Exception e) {
            Log.i(TAG, "EXCEPTION FOUND IN VAULT - START ALARM TIME:    "+e);
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
    //TODO Polymorphism: declaration of virtual methods
    public void SetSVault(Vault v) { }
    public Vault GetSVault(){return null;}
}
