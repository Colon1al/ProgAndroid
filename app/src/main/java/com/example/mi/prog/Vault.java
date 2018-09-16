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

public class Vault { // TODO: REWORK THIS SHIIIIIT
    public List<String> toCheerUpD  = new ArrayList<>(),
            writeToWhenBoredD = new ArrayList<>(),
            toDoToAchieveYourGoalsD = new ArrayList<>(),
            extraActivitiesD = new ArrayList<>();
    public String prefferedPlacesD,musicWithGoodMoodD,musicWithBadMoodD, NameD;
    int hourWorkSD,minuteWorkSD,hourWorkED,minuteWorkED,hourRestSD,minuteRestSD,hourRestED,minuteRestED;
   /* public void jsonHandicapWrite()
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
    }*/
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
           Date dateWS = sdf.parse(dateStr + dateStrEnd);
           Log.i("Vault", "dateWS Nice Alarm "+ dateWS);
           Intent intent = new Intent(context, AlarmNotificationReceiver.class);
           intent.putExtra("time",dateWS.toString());
           intent.putExtra("title",title);
           intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+hours*100 +mins);
           intent.putExtra("specialIntent",Special);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+hours*100 +mins, intent, FLAG_UPDATE_CURRENT);
           long time;
           time = dateWS.getTime();
           //Non-repeating alarm
           if(time>c.getTimeInMillis())
           {
               Log.i("Vault", "Alarm is ready to be set");
               am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
               Log.i("Vault", "Alarm is set");
           }

           return dateWS;
       } catch (Exception e){return null;}}
    private void AskUser(Context context,String text,String title,int id,String Special,AlarmManager am,long timeRand)
    {
        Log.i("Vault","AskUser started");
        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra("text",text);
        intent.putExtra("title", title);
        intent.putExtra("ID", 1);
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
            Date dateRS,dateRE,dateWS,dateWE;
            Log.i("Vault", "Before Nice Alarm func");
            dateWS = NiceAlarm("Пора за работу","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourWorkSD,V.GetSVault().minuteWorkSD);
            dateWE = NiceAlarm("Работа закончена, ура!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourWorkED,V.GetSVault().minuteWorkED);
            dateRS = NiceAlarm("Время для отдыха!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourRestSD,V.GetSVault().minuteRestSD);
            dateRE = NiceAlarm("Пора заканчивать отдых!","Basic",sdf,context,am,dateStr,c,V.GetSVault().hourRestED,V.GetSVault().minuteRestED);


            //WS
            /*if(V.GetSVault().hourWorkSD<=9)
                dateStrEnd = "0"+V.GetSVault().hourWorkSD+":";
            else
                dateStrEnd = V.GetSVault().hourWorkSD+":";
            if(V.GetSVault().minuteWorkSD<=9)
                dateStrEnd = dateStrEnd+"0"+V.GetSVault().minuteWorkSD+":00.000";
            else
                dateStrEnd = dateStrEnd+V.GetSVault().minuteWorkSD+":00.000";

            Date dateWS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWS.toString());
            intent.putExtra("title","Пора за работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourWorkSD*100 +V.GetSVault().minuteWorkSD);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourWorkSD*100 +V.minuteWorkSD, intent, FLAG_UPDATE_CURRENT);
            time = dateWS.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //WE
            if(V.GetSVault().hourWorkED<=9)
                dateStrEnd = "0"+V.GetSVault().hourWorkED+":";
            else
                dateStrEnd = V.GetSVault().hourWorkED+":";
            if(V.GetSVault().minuteWorkED<=9)
                dateStrEnd = dateStrEnd+"0"+V.GetSVault().minuteWorkED+":00.000";
            else
                dateStrEnd = dateStrEnd+V.GetSVault().minuteWorkED+":00.000";

            Date dateWE = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWE.toString());
            intent.putExtra("title","Пора закончить работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourWorkED*100 +V.GetSVault().minuteWorkED);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourWorkED*100 +V.GetSVault().minuteWorkED, intent, FLAG_UPDATE_CURRENT);
            time = dateWE.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RS
            if(V.GetSVault().hourRestSD<=9)
                dateStrEnd = "0"+V.GetSVault().hourRestSD+":";
            else
                dateStrEnd = V.GetSVault().hourRestSD+":";
            if(V.GetSVault().minuteRestSD<=9)
                dateStrEnd = dateStrEnd+"0"+V.GetSVault().minuteRestSD+":00.000";
            else
                dateStrEnd = dateStrEnd+V.GetSVault().minuteRestSD+":00.000";
            Date dateRS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(context, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateRS.toString());
            intent.putExtra("title","Пора отдыхать");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourRestSD*100 +V.GetSVault().minuteRestSD);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(context, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+V.GetSVault().hourRestSD*100 +V.GetSVault().minuteRestSD, intent, FLAG_UPDATE_CURRENT);
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
*/
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
    public void SetSVault(Vault v) { }
    public Vault GetSVault(){return null;}
}
