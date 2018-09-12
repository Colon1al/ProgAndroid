package com.example.mi.prog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import static android.content.ContentValues.TAG;

public class ScrollingActivity extends AppCompatActivity {
    int hourWorkSD,minuteWorkS,hourWorkE,minuteWorkE,hourRestS,minuteRestS,hourRestE,minuteRestE;
    CheckBox CatVideos;
    CheckBox Walk;
    CheckBox TvSeries;
    CheckBox Family;
    CheckBox Friend;
    CheckBox GF;
    CheckBox Pool;
    CheckBox Fit;
    CheckBox ADEM;
    CheckBox Dance;
    CheckBox Lang;
    CheckBox Drawing;
    EditText Name;
    EditText MoodG;
    EditText MoodB ;
    EditText MusicG ;
    EditText MusicB ;
    EditText Goals;
    EditText Places;
    EditText ActivExtra;
    Button WS,WE,RS,RE;
    Vault v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new Vault();
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CatVideos = (CheckBox) findViewById(R.id.checkBox2);
        Walk = (CheckBox) findViewById(R.id.checkBox3);
        TvSeries = (CheckBox) findViewById(R.id.checkBox4);
        Family = (CheckBox) findViewById(R.id.checkBox5);
        Friend = (CheckBox) findViewById(R.id.checkBox6);
        GF = (CheckBox) findViewById(R.id.checkBox7);
        Pool = (CheckBox) findViewById(R.id.checkBox9);
        Fit = (CheckBox) findViewById(R.id.checkBox11);
        ADEM = (CheckBox) findViewById(R.id.checkBox12);
        Dance = (CheckBox) findViewById(R.id.checkBox13);
        Lang = (CheckBox) findViewById(R.id.checkBox14);
        Drawing = (CheckBox) findViewById(R.id.checkBox15);
        Name = (EditText) findViewById(R.id.editText);
        MoodG = (EditText) findViewById(R.id.editText4);
        MoodB = (EditText) findViewById(R.id.editText5);
        MusicG = (EditText) findViewById(R.id.editText6);
        MusicB = (EditText) findViewById(R.id.editText7);
        Goals = (EditText) findViewById(R.id.editText8);
        Places = (EditText) findViewById(R.id.editText9);
        ActivExtra = (EditText) findViewById(R.id.editText10);
        WS = findViewById(R.id.button3);
        WE = findViewById(R.id.button4);
        RS = findViewById(R.id.button5);
        RE = findViewById(R.id.button6);

    }
    public void getDateWorkStart(View view)
    {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteP) {
                hourWorkSD = hourOfDay;
                minuteWorkS = minuteP;
                WS.setText(v.TimeNormaliser(hourOfDay,minuteP));


            }
        },hourWorkSD,minuteWorkS,true);
        dialog.show();
    }
    public void getDateWorkEnd(View view)
    {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteP) {
                hourWorkE = hourOfDay;
                minuteWorkE = minuteP;
                WE.setText(v.TimeNormaliser(hourOfDay,minuteP));
            }
        },hourWorkE,minuteWorkE,true);
        dialog.show();
    }
    public void getDateRestStart(View view)
    {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteP) {
                hourRestS = hourOfDay;
                minuteRestS = minuteP;
                RS.setText(v.TimeNormaliser(hourOfDay,minuteP));
            }
        },hourWorkSD,minuteWorkS,true);
        dialog.show();
    }
    public void getDateRestEnd(View view)
    {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteP) {
                hourRestE = hourOfDay;
                minuteRestE = minuteP;
                RE.setText(v.TimeNormaliser(hourOfDay,minuteP));
            }
        },hourWorkE,minuteWorkE,true);
        dialog.show();
    }
    public void Close(View view)
    {
        PersonInfo info = new PersonInfo();
        Vault vault = new Vault();
        vault.hourWorkSD=hourWorkSD;
        vault.minuteWorkS=minuteWorkS;
        vault.hourWorkE=hourWorkE;
        vault.minuteWorkE=minuteWorkE;
        vault.hourRestS=hourRestS;
        vault.minuteRestS=minuteRestS;
        vault.hourRestE=hourRestE;
        vault.minuteRestE=minuteRestE;
        vault.writeToWhenBored.clear();
        vault.extraActivities.clear();
        vault.toCheerUp.clear();
        vault.toDoToAchieveYourGoals.clear();
        if (CatVideos.isChecked())
            vault.toCheerUp.add("Посмотри видео с котиками");
        if (Walk.isChecked())
            vault.toCheerUp.add("Иди погуляй, хиккикомори");
        if (TvSeries.isChecked())
            vault.toCheerUp.add("Время посмотреть сериал !!!");
        if (Family.isChecked())
            vault.writeToWhenBored.add("Может стоит позвонить родственникам ?");
        if (Friend.isChecked())
            vault.writeToWhenBored.add("Может стоит позвонить друзьям ?");
        if (Pool.isChecked())
            vault.extraActivities.add("Сходи в бассейн, подготовься к лету) ");
        if (Fit.isChecked())
            vault.extraActivities.add("Сходи на фитнес подготовься к лету)");
        if (ADEM.isChecked())
            vault.extraActivities.add("Компьютерная графика: ADEM сила, Fusion360 могила !!!");
        if (Dance.isChecked())
            vault.extraActivities.add("Танцы");
        if (Lang.isChecked())
            vault.extraActivities.add("Изучение языков");
        if (Drawing.isChecked())
            vault.extraActivities.add("Рисование очень хорошо расслабляет");
        vault.toCheerUp.add(MoodG.getText().toString());
        vault.writeToWhenBored.add(MoodB.getText().toString());
        vault.toDoToAchieveYourGoals.add(Goals.getText().toString());
        vault.prefferedPlacesD=Places.getText().toString();

        vault.extraActivities.add(ActivExtra.getText().toString());
        vault.musicWithBadMood = MusicB.getText().toString();
        vault.musicWithGoodMood = MusicG.getText().toString();
        vault.Name=Name.getText().toString();
        info.writeFile(ScrollingActivity.this);
        vault.startAlarmTime(vault,this);

        finish();
    }
}


