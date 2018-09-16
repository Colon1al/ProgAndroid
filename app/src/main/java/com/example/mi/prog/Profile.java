package com.example.mi.prog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;
//This is a simple class that displays info filled up in the questionnaire
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
        try {
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

            List<String> lst = v.GetSVault().writeToWhenBoredD;
            for (int i = 0; i < lst.size(); ++i) {
                if (lst.get(i).equals("Может стоит позвонить родственникам ?"))
                    lst.set(i, "Родственники");
                if (lst.get(i).equals("Может стоит позвонить друзьям ?")) lst.set(i, "Друзья");
            }
            MoodB.setText(ListToStringProperly(lst.toString()));

            lst = v.GetSVault().toCheerUpD;
            for (int i = 0; i < lst.size(); ++i) {
                if (lst.get(i).equals("Посмотри видео с котиками")) lst.set(i, "Видео с котиками");
                if (lst.get(i).equals("Иди погуляй, хиккикомори")) lst.set(i, "Прогулка");
                if (lst.get(i).equals("Время посмотреть сериал !!!")) lst.set(i, "Сериал");
            }
            MoodG.setText(ListToStringProperly(lst.toString()));

            lst = v.GetSVault().extraActivitiesD;
            for (int i = 0; i < lst.size(); ++i) {
                if (lst.get(i).equals("Сходи в бассейн, подготовься к лету) "))
                    lst.set(i, "Бассейн");
                if (lst.get(i).equals("Сходи на фитнес подготовься к лету)")) lst.set(i, "Фитнес");
                if (lst.get(i).equals("Компьютерная графика: ADEM сила, Fusion360 могила !!!"))
                    lst.set(i, "Компьютерная графика");
                if (lst.get(i).equals("Рисование очень хорошо расслабляет"))
                    lst.set(i, "Рисование");
            }
            ActivExtra.setText(ListToStringProperly(lst.toString()));

            tmp = v.GetSVault().toDoToAchieveYourGoalsD.toString();
            Goals.setText(ListToStringProperly(tmp));

            tmp = v.GetSVault().prefferedPlacesD;
            Places.setText(ListToStringProperly(tmp));

            WS.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourWorkSD, v.GetSVault().minuteWorkSD));
            WE.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourWorkED, v.GetSVault().minuteWorkED));
            RS.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourRestSD, v.GetSVault().minuteRestSD));
            RE.setText(v.GetSVault().TimeNormaliser(v.GetSVault().hourRestED, v.GetSVault().minuteRestED));
        }
        catch (Exception e)
        {
            Log.i("Profile", "Profile - OnCreate - exception:" + e);
        }
    }
    //This method converts list to String and removes brackets and unnecessary commas
    String ListToStringProperly(String lst)
    {
        if (lst == "[]")
            return "";
        String t = lst;
        for (int i=0;i<t.length();i++)
            if(t.charAt(i)=='[' || t.charAt(i)==']')
                t = t.substring(0,i)+ "" + t.substring(i+1);

        for (int i=0;i<t.length()-2;i++)
            if(t.charAt(i)==',' && t.charAt(i+1)==' ' && t.charAt(i+2)==',')
            {
                t = t.substring(0,i)+ "" + t.substring(i+2);
                i--;
            }

        if(t.charAt(t.length()-2)==',' && t.charAt(t.length()-1)==' ')
            t = t.substring(0,t.length()-2)+ "";

        return t;
    }
}
