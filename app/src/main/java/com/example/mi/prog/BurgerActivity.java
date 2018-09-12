package com.example.mi.prog;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mi.prog.fragments.ListClass;
import com.example.mi.prog.fragments.testfragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.ContentValues.TAG;

public class BurgerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment testfragment1,testfragment2,testfragment3;
    TextView T;
    static boolean fragmentCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);
        File f = new File("/data/data/com.example.mi.prog/files/UserInfo.data");
        if (!f.getAbsoluteFile().exists()) {
            Intent intent1 = new Intent(BurgerActivity.this, ScrollingActivity.class);
            startActivityForResult(intent1,90);
            Intent intent = new Intent(BurgerActivity.this, InfoActivity.class);
            startActivity(intent);
            }
        else {
            PersonInfo info = new PersonInfo();
            info.readFile(BurgerActivity.this);
            SetName();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!fragmentCheck || getIntent().getIntExtra("m", 0) == 1) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            testfragment1 = new ListClass();
            Bundle tmp = new Bundle();
            Calendar c = Calendar.getInstance();
            tmp.putInt("year", c.get(Calendar.YEAR));
            tmp.putInt("month", c.get(Calendar.MONTH));
            tmp.putInt("day", c.get(Calendar.DAY_OF_MONTH));
            testfragment1.setArguments(tmp);
            fragmentTransaction.add(R.id.container, testfragment1);
            fragmentTransaction.commit();
            fragmentCheck = true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            String channelId = "ItsYourTime";

            CharSequence channelName = "ItsYourTime";

            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);

            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(Color.RED);

            notificationChannel.enableVibration(true);

            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void SetName()
    {
        Vault v = new Vault();
        NavigationView n = findViewById(R.id.nav_view);
        View header = n.getHeaderView(0);
        T = header.findViewById(R.id.UserName);
        T.setText(v.GetSVault().NameD);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        testfragment3 = new ListClass();
        testfragment1 = new testfragment();
        // Handle navigation view item clicks here.
        Bundle tmp = new Bundle();
        Calendar c = Calendar.getInstance();
        tmp.putInt("year", c.get(Calendar.YEAR));
        tmp.putInt("month", c.get(Calendar.MONTH));
        tmp.putInt("day", c.get(Calendar.DAY_OF_MONTH));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(this,ScrollingActivity.class);
            startActivityForResult(i,90);
        } else if (id == R.id.nav_gallery) {
            fragmentTransaction.replace(R.id.container, testfragment1);
        } else if (id == R.id.nav_slideshow) {
            testfragment3.setArguments(tmp);
            fragmentTransaction.replace(R.id.container, testfragment3);
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                EventClass newInfo = (EventClass) data.getSerializableExtra("Data");

                if (newInfo != null)

                {
                    LISTDATA list1 = new LISTDATA();
                    list1.add(this, newInfo);
                    startAlarm(newInfo);
                    Bundle tmp = new Bundle();
                    Calendar c = Calendar.getInstance();
                    tmp.putInt("year", newInfo.getYear());
                    tmp.putInt("month", newInfo.getMonth());
                    tmp.putInt("day", newInfo.getDay());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    testfragment3 = new ListClass();
                    testfragment3.setArguments(tmp);
                    fragmentTransaction.replace(R.id.container, testfragment3);
                    fragmentTransaction.commit();

                }
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            LISTDATA list1 = new LISTDATA();
            Bundle tmp = new Bundle();
            tmp.putInt("year", data.getIntExtra("year", 1970));
            tmp.putInt("month", data.getIntExtra("month", 0));
            tmp.putInt("day", data.getIntExtra("day", 0));
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            testfragment3 = new ListClass();
            testfragment3.setArguments(tmp);
            fragmentTransaction.replace(R.id.container, testfragment3);
            list1.writeFile(this);
            fragmentTransaction.commit();
        }
        if (requestCode==90)
        {SetName();
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
                notification.setAutoCancel(true);
                notification.setDefaults(Notification.DEFAULT_ALL);
                notification.setDefaults(Notification.DEFAULT_LIGHTS);
                notification.setDefaults(Notification.DEFAULT_SOUND);
                notification.setWhen(System.currentTimeMillis());
                notification.setSmallIcon(R.drawable.ic_info_black_24dp);
                //notification.setTicker(ticker);
                notification.setContentText("Молодец, ты заполнил анкету <3");
                notification.setContentTitle("Достижение");
                notification.setContentInfo("");
                notification.setChannelId("ItsYourTime");
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                try {
                    nm.notify(10, notification.build());
                }catch(Exception e){Log.i(TAG,e.toString());}
        startAlarmTime(new Vault());
        }
    }
    public void OPENPROFILE(View v)
    {
        File f = new File("/data/data/com.example.mi.prog/files/UserInfo.data");
        if(!f.getAbsoluteFile().exists())
        {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer_layout),"Заполни анкету ",Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        else{
        Intent intent = new Intent(BurgerActivity.this,Profile.class);
        startActivity(intent);}
    }
    public void startAlarm(EventClass Event) {
        try {
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(Event.getYear() + "-" + (Event.getMonth() + 1) + "-" + Event.getDay() + " " + Event.getHours() + ":" + Event.getMinutes() + ":" + "00.000");
            intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
            intent.putExtra("time",date.toString());
            intent.putExtra("title",Event.getName());
            intent.putExtra("text",Event.getInfo());
            intent.putExtra("ID",Event.getDay()*10000+Event.getMonth()*1000000+Event.getYear()*100000000+Event.getHours()*100+Event.getMinutes());
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, Event.getDay()+Event.getMonth()+Event.getYear()+Event.getHours()+Event.getMinutes(), intent, FLAG_UPDATE_CURRENT);
            long time = date.getTime();
            //Non-repeating alarm
            am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            //Repeating alarm
            //am.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime() + time, time, pendingIntent);
        } catch (Exception e) { }
    }
    public void startAlarmTime(Vault v) {
        try {
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
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
            if(v.GetSVault().hourWorkSD<=9)
                dateStrEnd = "0"+v.GetSVault().hourWorkSD+":";
            else
                dateStrEnd = v.GetSVault().hourWorkSD+":";
            if(v.GetSVault().minuteWorkSD<=9)
                dateStrEnd = dateStrEnd+"0"+v.GetSVault().minuteWorkSD+":00.000";
            else
                dateStrEnd = dateStrEnd+v.GetSVault().minuteWorkSD+":00.000";

            Date dateWS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWS.toString());
            intent.putExtra("title","Пора за работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourWorkSD*100 +v.GetSVault().minuteWorkSD);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourWorkSD*100 +v.GetSVault().minuteWorkSD, intent, FLAG_UPDATE_CURRENT);
            time = dateWS.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //WE
            if(v.GetSVault().hourWorkED<=9)
                dateStrEnd = "0"+v.GetSVault().hourWorkED+":";
            else
                dateStrEnd = v.GetSVault().hourWorkED+":";
            if(v.GetSVault().minuteWorkED<=9)
                dateStrEnd = dateStrEnd+"0"+v.GetSVault().minuteWorkED+":00.000";
            else
                dateStrEnd = dateStrEnd+v.GetSVault().minuteWorkED+":00.000";

            Date dateWE = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateWE.toString());
            intent.putExtra("title","Пора закончить работу");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourWorkED*100 +v.GetSVault().minuteWorkED);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourWorkED*100 +v.GetSVault().minuteWorkED, intent, FLAG_UPDATE_CURRENT);
            time = dateWE.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RS
            if(v.GetSVault().hourRestSD<=9)
                dateStrEnd = "0"+v.GetSVault().hourRestSD+":";
            else
                dateStrEnd = v.GetSVault().hourRestSD+":";
            if(v.GetSVault().minuteRestSD<=9)
                dateStrEnd = dateStrEnd+"0"+v.GetSVault().minuteRestSD+":00.000";
            else
                dateStrEnd = dateStrEnd+v.GetSVault().minuteRestSD+":00.000";
            Date dateRS = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateRS.toString());
            intent.putExtra("title","Пора отдыхать");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourRestSD*100 +v.GetSVault().minuteRestSD);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourRestSD*100 +v.GetSVault().minuteRestSD, intent, FLAG_UPDATE_CURRENT);
            time = dateRS.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RE:ZERO - 
            if(v.GetSVault().hourRestED<=9)
                dateStrEnd = "0"+v.GetSVault().hourRestED+":";
            else
                dateStrEnd = v.GetSVault().hourRestED+":";
            if(v.GetSVault().minuteRestED<=9)
                dateStrEnd = dateStrEnd+"0"+v.GetSVault().minuteRestED+":00.000";
            else
                dateStrEnd = dateStrEnd+v.GetSVault().minuteRestED+":00.000";
            Date dateRE = sdf.parse(dateStr + dateStrEnd);
            intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
            intent.putExtra("time",dateRE.toString());
            intent.putExtra("title","Пора перестать отдыхать");
            intent.putExtra("ID",c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourRestED*100 +v.GetSVault().minuteRestED);
            intent.putExtra("specialIntent","Basic");
            pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, c.get(Calendar.YEAR)*100000000+c.get(Calendar.MONTH)*1000000+c.get(Calendar.DAY_OF_MONTH)*10000+v.GetSVault().hourRestED*100 +v.GetSVault().minuteRestED, intent, FLAG_UPDATE_CURRENT);
            time = dateRE.getTime();
            //Non-repeating alarm
            if(time>c.getTimeInMillis())
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            //RestRandom
            if(dateRE.getTime()>dateRS.getTime()) {
                long timeRand = ThreadLocalRandom.current().nextLong(dateRS.getTime(), dateRE.getTime());
                //Randomising the text on notification
                int whatToSay =3;// ThreadLocalRandom.current().nextInt(0,5);
                int i;
                switch (whatToSay)
                {
                    case 0://toCheerUpD
                        intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,v.GetSVault().toCheerUpD.size());
                        intent.putExtra("text",v.GetSVault().toCheerUpD.get(i));
                        intent.putExtra("title", "Тебе грустно?");
                        intent.putExtra("ID", 1);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 1://writeToWhenNored
                        intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,v.GetSVault().writeToWhenBoredD.size());
                        intent.putExtra("text",v.GetSVault().writeToWhenBoredD.get(i));
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 2);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 2://toDoToAchieveYourGoalsD
                        intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
                        i = ThreadLocalRandom.current().nextInt(0,v.GetSVault().toDoToAchieveYourGoalsD.size());
                        intent.putExtra("text","Займись этим: "+v.GetSVault().toDoToAchieveYourGoalsD.get(i));
                        intent.putExtra("title", "Нечего делать? Время достигать свои цели!");
                        intent.putExtra("ID", 3);
                        intent.putExtra("specialIntent","Basic");
                        pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 3://prefferedPlacesD
                        intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
                        intent.putExtra("specialIntent","places");
                        intent.putExtra("text","Не сходить ли тебе сюда: "+v.GetSVault().prefferedPlacesD);
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 4);
                        pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                    case 4://Sending notifications to user randomly
                        intent = new Intent(BurgerActivity.this, AlarmNotificationReceiver.class);
                        intent.putExtra("specialIntent","question");
                        intent.putExtra("text"," ");
                        intent.putExtra("title", "Тебе скучно?");
                        intent.putExtra("ID", 5);
                        pendingIntent = PendingIntent.getBroadcast(BurgerActivity.this, 0, intent, FLAG_UPDATE_CURRENT);
                        //Non-repeating alarm
                        am.set(AlarmManager.RTC_WAKEUP, timeRand, pendingIntent);
                        break;

                }
            }

        } catch (Exception e) {
            Log.i(TAG, "EXCEPTION FOUND IN START ALARM TIME:    "+e);
        }
    }

}
