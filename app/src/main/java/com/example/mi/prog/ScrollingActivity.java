package com.example.mi.prog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
//This class describes how Scrolling Activity will work. This Activity is a questionnaire, where user fills up all the data. This class gets this data and stores it in the Vault
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
        vault.minuteWorkSD=minuteWorkS;
        vault.hourWorkED=hourWorkE;
        vault.minuteWorkED=minuteWorkE;
        vault.hourRestSD=hourRestS;
        vault.minuteRestSD=minuteRestS;
        vault.hourRestED=hourRestE;
        vault.minuteRestED=minuteRestE;
        vault.writeToWhenBoredD.clear();
        vault.extraActivitiesD.clear();
        vault.toCheerUpD.clear();
        vault.toDoToAchieveYourGoalsD.clear();
        if (CatVideos.isChecked())
            vault.toCheerUpD.add("Посмотри видео с котиками");
        if (Walk.isChecked())
            vault.toCheerUpD.add("Иди погуляй, хиккикомори");
        if (TvSeries.isChecked())
            vault.toCheerUpD.add("Время посмотреть сериал !!!");
        if (Family.isChecked())
            vault.writeToWhenBoredD.add("Может стоит позвонить родственникам ?");
        if (Friend.isChecked())
            vault.writeToWhenBoredD.add("Может стоит позвонить друзьям ?");
        if (Pool.isChecked())
            vault.extraActivitiesD.add("Сходи в бассейн, подготовься к лету) ");
        if (Fit.isChecked())
            vault.extraActivitiesD.add("Сходи на фитнес подготовься к лету)");
        if (ADEM.isChecked())
            vault.extraActivitiesD.add("Компьютерная графика: ADEM сила, Fusion360 могила !!!");
        if (Dance.isChecked())
            vault.extraActivitiesD.add("Танцы");
        if (Lang.isChecked())
            vault.extraActivitiesD.add("Изучение языков");
        if (Drawing.isChecked())
            vault.extraActivitiesD.add("Рисование очень хорошо расслабляет");
        vault.toCheerUpD.add(MoodG.getText().toString());
        vault.writeToWhenBoredD.add(MoodB.getText().toString());
        vault.toDoToAchieveYourGoalsD.add(Goals.getText().toString());
        vault.prefferedPlacesD=Places.getText().toString();
        vault.extraActivitiesD.add(ActivExtra.getText().toString());
        vault.musicWithBadMoodD = MusicB.getText().toString();
        vault.musicWithGoodMoodD = MusicG.getText().toString();
        vault.NameD=Name.getText().toString();
        info.writeFile(ScrollingActivity.this,vault);
        StaticVault vS = new StaticVault();
        vS.SetSVault(vault);
        vault.startAlarmTime(vS,this);

        finish();
    }
}


