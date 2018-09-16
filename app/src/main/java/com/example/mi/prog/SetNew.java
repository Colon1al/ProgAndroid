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

import java.util.Calendar;

//This class is very similar to EditActivity, but its purpose is to create a new event. Why did we create two similar classes?
//1) - delete button is not here
//2) - we do not get event data - we create it
public class SetNew extends AppCompatActivity {


    EditText edit,edit2;
    RadioButton E,EDay,EWeek,EMonth,EYear;
    Button getdate;
    int year,month,day,minute,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new);

        edit = (EditText)findViewById(R.id.editText);
        edit2 = (EditText)findViewById(R.id.editText2);
        E = findViewById(R.id.radioButton8);
        EDay = findViewById(R.id.radioButton7);
        EWeek = findViewById(R.id.radioButton6);
        EMonth = findViewById(R.id.radioButton5);
        EYear = findViewById(R.id.radioButton4);
        getdate = findViewById(R.id.getdate);
        Calendar c = Calendar.getInstance();

        year = getIntent().getIntExtra("year",1970);
        month = getIntent().getIntExtra("month",0);
        day = getIntent().getIntExtra("day", 1);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
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

    public void saveEvent(View v)
    {
        int repetition = 0;
        if (E.isChecked())
            repetition = 0;
        if (EDay.isChecked())
            repetition = 1;
        if (EWeek.isChecked())
            repetition = 2;
        if (EMonth.isChecked())
            repetition = 3;
        if (EYear.isChecked())
            repetition = 4;
        EventClass data = new EventClass(edit2.getText().toString(), edit.getText().toString(),year,month,day,hour,minute,repetition);
        Intent intent = new Intent(SetNew.this, BurgerActivity.class);
        intent.putExtra("Data", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}