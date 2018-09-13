package com.example.mi.prog;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Profile extends AppCompatActivity {
    String tmp;
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
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        v = new StaticVault();
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
        Name.setText(v.GetSVault().NameD);
        MusicG.setText(v.GetSVault().musicWithGoodMoodD);
        MusicB.setText(v.GetSVault().musicWithBadMoodD);
        List<String> lst  = v.GetSVault().writeToWhenBoredD;
        for (int i=0;i<lst.size();++i)
        {
            if(lst.get(i).equals("Может стоит позвонить родственникам ?")) lst.set(i,"Роственники");
            if(lst.get(i).equals("Может стоит позвонить друзьям ?")) lst.set(i,"Друзья");
        }
        tmp = lst.toString();
        for (int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='[' || tmp.charAt(i)==']')
            {
                tmp = tmp.substring(0,i)+tmp.substring(i+1);
            }
        }
        MoodB.setText(tmp);
        lst = v.GetSVault().toCheerUpD;
        for (int i=0;i<lst.size();++i)
        {
            if(lst.get(i).equals("Посмотри видео с котиками")) lst.set(i,"Видео с котиками");
            if(lst.get(i).equals("Иди погуляй, хиккикомори")) lst.set(i,"Прогулка");
            if(lst.get(i).equals("Время посмотреть сериал !!!")) lst.set(i,"Сериал");
        }
        tmp = lst.toString();
        for (int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='['||tmp.charAt(i)==']')
            {
                tmp = tmp.substring(0,i) + "" + tmp.substring(i+1);
            }
        }
        MoodG.setText(tmp);
        lst = v.GetSVault().extraActivitiesD;
        for (int i=0;i<lst.size();++i)
        {
            if(lst.get(i).equals("Сходи в бассейн, подготовься к лету) ")) lst.set(i,"Бассейн");
            if(lst.get(i).equals("Сходи на фитнес подготовься к лету)")) lst.set(i,"Фитнес");
            if(lst.get(i).equals("Компьютерная графика: ADEM сила, Fusion360 могила !!!")) lst.set(i,"Компютерная графика");
            if(lst.get(i).equals("Рисование очень хорошо расслабляет")) lst.set(i,"Рисовани");
        }
        tmp = lst.toString();
        for (int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='['||tmp.charAt(i)==']')
            {
                tmp = tmp.substring(0,i) + "" + tmp.substring(i+1);
            }
        }
        ActivExtra.setText(tmp);
        tmp = v.GetSVault().toDoToAchieveYourGoalsD.toString();
        for (int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='['||tmp.charAt(i)==']')
            {
                tmp = tmp.substring(0,i) + "" + tmp.substring(i+1);
            }
        }
        Goals.setText(tmp);
        tmp = v.GetSVault().prefferedPlacesD;
        for (int i=0;i<tmp.length();i++)
        {
            if(tmp.charAt(i)=='+')
            {
                tmp = tmp.substring(0,i) + ", " + tmp.substring(i+1);
            }
        }
        Places.setText(tmp);
        WS.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourWorkSD,v.GetSVault().minuteWorkSD));
        WE.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourWorkED,v.GetSVault().minuteWorkED));
        RS.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourRestSD,v.GetSVault().minuteRestSD));
        RE.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourRestED,v.GetSVault().minuteRestED));
    }
}
