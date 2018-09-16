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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mi.prog.fragments.ListClass;
import com.example.mi.prog.fragments.testfragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.ContentValues.TAG;
//Main Activity of the app
public class BurgerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragmentListClass,fragmentCalendar;
    static boolean fragmentCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);
        //Checking if there is any info about the user
        File f = new File("/data/data/com.example.mi.prog/files/UserInfo.data");
        if (!f.getAbsoluteFile().exists()) {
            //if there is no info, Scrolling activity starts (there, user can complete profile data)
            Intent intent1 = new Intent(BurgerActivity.this, ScrollingActivity.class);
            startActivityForResult(intent1,90);
            //a short info activity also starts to explain what the app does
            Intent intent = new Intent(BurgerActivity.this, InfoActivity.class);
            startActivity(intent);
            }
        else {
            //if there is info, we read it using Person info class
            PersonInfo info = new PersonInfo();
            info.readFile(BurgerActivity.this);
            //Setting username in the toolbar from the data loaded
            SetName();
        }
        //Setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!fragmentCheck || getIntent().getIntExtra("m", 0) == 1) {
            //Creating a fragment with a list of all events
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentListClass = new ListClass();
            Bundle tmp = new Bundle();
            Calendar c = Calendar.getInstance();
            tmp.putInt("year", c.get(Calendar.YEAR));
            tmp.putInt("month", c.get(Calendar.MONTH));
            tmp.putInt("day", c.get(Calendar.DAY_OF_MONTH));
            fragmentListClass.setArguments(tmp);
            fragmentTransaction.add(R.id.container, fragmentListClass);
            fragmentTransaction.commit();
            fragmentCheck = true;
        }
        //Setting up the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Setting up the navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Setting up some settings for notifications for Android Oreo+
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
    //This method sets the name of the user using data from user info file
    public void SetName()
    {
        Vault v = new StaticVault();
        NavigationView n = findViewById(R.id.nav_view);
        View header = n.getHeaderView(0);
        TextView T = header.findViewById(R.id.UserName);
        T.setText(v.GetSVault().NameD);
    }

    //This method sets up the navigation bar buttons
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragmentListClass = new ListClass();
        fragmentCalendar = new testfragment();
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
            fragmentTransaction.replace(R.id.container, fragmentCalendar);
        } else if (id == R.id.nav_slideshow) {
            fragmentListClass.setArguments(tmp);
            fragmentTransaction.replace(R.id.container, fragmentListClass);
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //This method is called when activities send results back to Burger Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //New event created
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                EventClass newInfo = (EventClass) data.getSerializableExtra("Data");

                if (newInfo != null)
                {
                    //adding the event to the current array of events
                    LISTDATA list1 = new LISTDATA();
                    list1.add(this, newInfo);
                    //setting up the notification for the new event
                    startAlarm(newInfo);
                    Bundle tmp = new Bundle();
                    tmp.putInt("year", newInfo.getYear());
                    tmp.putInt("month", newInfo.getMonth());
                    tmp.putInt("day", newInfo.getDay());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentListClass = new ListClass();
                    fragmentListClass.setArguments(tmp);
                    fragmentTransaction.replace(R.id.container, fragmentListClass);
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
            fragmentListClass = new ListClass();
            fragmentListClass.setArguments(tmp);
            fragmentTransaction.replace(R.id.container, fragmentListClass);
            list1.writeFile(this);
            fragmentTransaction.commit();
        }
        if (requestCode==90)
        {
            SetName();
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);
            notification.setDefaults(Notification.DEFAULT_ALL);
            notification.setDefaults(Notification.DEFAULT_LIGHTS);
            notification.setDefaults(Notification.DEFAULT_SOUND);
            notification.setWhen(System.currentTimeMillis());
            notification.setSmallIcon(R.drawable.ic_info_black_24dp);
            notification.setContentText("Молодец, ты заполнил анкету <3");
            notification.setContentTitle("Достижение");
            notification.setContentInfo("");
            notification.setChannelId("ItsYourTime");
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            try {
                nm.notify(10, notification.build());
            }catch(Exception e){Log.i(TAG,e.toString());}

            StaticVault vs = new StaticVault();
            vs.startAlarmTime(vs, this);
        }
    }
    //This method is called when user presses on the profile pic
    public void OPENPROFILE(View v)
    {
        File f = new File("/data/data/com.example.mi.prog/files/UserInfo.data");
        //If there is no info, user has to fill it up first
        if(!f.getAbsoluteFile().exists())
        {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer_layout),"Заполни анкету ",Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
        //Else, open up the profile activity
        else {
            Intent intent = new Intent(BurgerActivity.this,Profile.class);
            startActivity(intent);
        }
    }
    //This method sets up the alarm for the new event
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
        } catch (Exception e) { Log.i(TAG,"BurgerActivity - startAlarm - exception=" + e);}
    }

}
