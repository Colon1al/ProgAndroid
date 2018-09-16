package com.example.mi.prog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
//This class describes the activity in which an event can be edited
public class EditEventActivity extends AppCompatActivity {
    EditText name;
    EditText info;
    RadioButton E, Eday,Eweek,Emonth,Eyear;
    Button getdate;
    int minute,hour,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        name = findViewById(R.id.editText2);
        info = findViewById(R.id.editText);
        E = findViewById(R.id.radioButton8);
        Eday = findViewById(R.id.radioButton7);
        Eweek = findViewById(R.id.radioButton6);
        Emonth = findViewById(R.id.radioButton5);
        Eyear = findViewById(R.id.radioButton4);
        getdate = findViewById(R.id.getdate);


        m = getIntent().getIntExtra("position", -1);
        if(m!=-1)
        {
            LISTDATA data = new LISTDATA();
            name.setText(data.data.get(m).getName());
            info.setText(data.data.get(m).getInfo());
            switch (data.data.get(m).getRepeat())
            {
                case 0:E.setChecked(true); break;
                case 1:Eday.setChecked(true); break;
                case 2:Eweek.setChecked(true); break;
                case 3:Emonth.setChecked(true); break;
                case 4:Eyear.setChecked(true); break;
            }
            hour = data.data.get(m).getHours();
            minute = data.data.get(m).getMinutes();
        }


    }
    public void deleteEvent(View v)
    {
        LISTDATA del = new LISTDATA();
        int m = getIntent().getIntExtra("position",-1);
        if(m!=-1)
        {
            Intent intent1 = new Intent(EditEventActivity.this,BurgerActivity.class);
            intent1.putExtra("year",del.data.get(m).getYear());
            intent1.putExtra("month",del.data.get(m).getMonth());
            intent1.putExtra("day",del.data.get(m).getDay());
            setResult(RESULT_OK, intent1);
            if (del.data.get(m).getRepeat()>0)
            del.deleteRepetition(this,del.data.get(m));
            del.data.remove(m);
            finish();
        }

    }
    public void getDate(View v)
    {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteP) {
                hour = hourOfDay;
                minute = minuteP;
                if(hourOfDay<10&&minuteP<10)
                    getdate.setText("0"+hourOfDay+" : 0"+minuteP);
                else {
                    if (hourOfDay < 10)
                        getdate.setText("0" + hourOfDay + " : " + minuteP);
                    else
                    if (minuteP < 10)
                        getdate.setText(hourOfDay + " : 0" + minuteP);
                    else
                        getdate.setText(hourOfDay+" : "+minuteP);
                }
            }
        },hour,minute,true);
        dialog.show();
    }
    public void saveEditEvent(View v)
    {
        LISTDATA del = new LISTDATA();

        EventClass data = del.data.get(m);
        del.deleteRepetition(this,data);
        if(E.isChecked()) data.setRepeat(0);
        if(Eday.isChecked()) data.setRepeat(1);
        if(Eweek.isChecked()) data.setRepeat(2);
        if(Emonth.isChecked()) data.setRepeat(3);
        if(Eyear.isChecked()) data.setRepeat(4);

        data.setInfo(info.getText().toString());
        data.setName(name.getText().toString());
        data.setHours(hour);
        data.setMinutes(minute);
        if(data.getRepeat()!=0) del.writeRepetive(this,data);
        Intent intent = new Intent(EditEventActivity.this, BurgerActivity.class);
        intent.putExtra("year",del.data.get(m).getYear());
        intent.putExtra("month",del.data.get(m).getMonth());
        intent.putExtra("day",del.data.get(m).getDay());
        setResult(RESULT_OK,intent);
        finish();
}   }
