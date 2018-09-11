package com.example.mi.prog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    // DON'T FORGET TO ADD "  <receiver android:name=".AlarmNotificationReceiver"/>   " into manifests -> AndroidManifest.xml
    // ALSO " implementation 'com.android.support:support-v4:27.1.1' " into Gradle Scripts -> build.gradle(Module: app) -> dependencies {}

    public String ticker="Ticker";
    public String title="Календарь";
    public String text="Пришло время события";
    public String info="Название события";

    public String channelId = "ItsYourTime";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        notification.setDefaults(Notification.DEFAULT_ALL);
        notification.setDefaults(Notification.DEFAULT_LIGHTS);
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setWhen(System.currentTimeMillis());
        notification.setSmallIcon(R.drawable.ic_menu_send);
        //notification.setTicker(ticker);
        notification.setContentText(intent.getStringExtra("text"));
        notification.setContentTitle(intent.getStringExtra("title"));
        notification.setContentInfo(intent.getStringExtra("time"));
        notification.setChannelId(channelId);
        Log.i(TAG,"NUMBER: "+intent.getIntExtra("ID",0));


        String specialIntent = "Basic";

        try
        {
            specialIntent = intent.getStringExtra("specialIntent");
            Log.i(TAG,"TRY ! SpecialIntent "+ specialIntent);
        }
        catch (Exception e)
        {
            specialIntent = "Basic";
            Log.i(TAG,"CATCH ! SpecialIntent "+ specialIntent);
        }
        if (specialIntent.equals("places"))
        {
            Vault v = new Vault();
            Log.i(TAG,"ВУИГПЖ !!!!!!!!!!: "+ v.prefferedPlaces);
            for (int i=0;i<v.prefferedPlaces.length();i++)
            {
                if (v.prefferedPlaces.charAt(i) != ' ') {
                    v.prefferedPlaces = v.prefferedPlaces.substring(i);
                    break;
                }
            }
            for (int i=0;i<v.prefferedPlaces.length();i++)
            {
                if(v.prefferedPlaces.charAt(i)==' ')
                {
                    v.prefferedPlaces = v.prefferedPlaces.substring(0,i) + "+" + v.prefferedPlaces.substring(i+1);
                }
            }
            Intent intentForNotification = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.ru/maps/search/"+v.prefferedPlaces+"/"));
            Log.i(TAG,"After Uri Plases Notif Res");
            intentForNotification.putExtra("m", 1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentForNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);
            Log.i(TAG,"After Uri Plases Notif Res");
        }
        else if (specialIntent.equals("question"))
        {
            Log.i(TAG,"After Dialog StartActivity");
            Intent intentForNotification = new Intent(context, DialogActivity.class);
            intentForNotification.putExtra("m", 1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentForNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);
            Log.i(TAG,"After Dialog StartActivity");
        }
        else {
            Intent intentForNotification = new Intent(context, BurgerActivity.class);
            intentForNotification.putExtra("m", 1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentForNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);
        }
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            nm.notify(intent.getIntExtra("ID", 0), notification.build());
        }catch(Exception e){Log.i(TAG,e.toString());}
    }
}
