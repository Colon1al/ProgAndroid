package com.example.mi.prog.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.mi.prog.AdapterForEvents;
import com.example.mi.prog.EditEventActivity;
import com.example.mi.prog.EventClass;
import com.example.mi.prog.LISTDATA;
import com.example.mi.prog.R;
import com.example.mi.prog.SetNew;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class ListClass extends ListFragment
{
    static int currM = 0;//TODO:govnocode

    @Override

    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        LISTDATA l = new LISTDATA();
        final Bundle transfer = this.getArguments();
        final int m = transfer.getInt("month");
        final int y = transfer.getInt("year");
        final int d = transfer.getInt("day");
        l.setLmonth(transfer.getInt("month"));
        l.setLyear(transfer.getInt("year"));
        if (currM!=transfer.getInt("month"))
        {
            currM = transfer.getInt("month");
           l.readFile(getActivity());
        }
            FloatingActionButton fab = getView().findViewById(R.id.floatingActionButton2);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SetNew.class);
                    intent.putExtra("month",m);
                    intent.putExtra("year",y);
                    intent.putExtra("day",d);
                    getActivity().startActivityForResult(intent, 1);
                }
            });
            ArrayList<EventClass> arrayOfEvent = new ArrayList<>();
            AdapterForEvents adapter = new AdapterForEvents(getActivity(), arrayOfEvent);
            if (l.data!=null)
            for (int i = 0; i < l.data.size(); ++i)
            {
                Log.i(TAG, "month:"+Integer.toString(l.data.get(i).getMonth()));
                Log.i(TAG, "month:"+Integer.toString(transfer.getInt("month")));
                Log.i(TAG, "day:"+Integer.toString(l.data.get(i).getDay()));
                Log.i(TAG, "day:"+Integer.toString(transfer.getInt("day")));
                Log.i(TAG, "year:"+Integer.toString(l.data.get(i).getYear()));
                Log.i(TAG, "year:"+Integer.toString(transfer.getInt("year")));
                if (l.data.get(i).getMonth() == m && l.data.get(i).getDay() == d && l.data.get(i).getYear() == y && l.data.get(i).getRepeat()==0)
                {
                    adapter.add(l.data.get(i));
                }
                if(l.data.get(i).getRepeat()>0) //event is repeating
                {
                    switch(l.data.get(i).getRepeat())
                    {
                        case 1://EDay
                            adapter.add(l.data.get(i));
                            break;
                        case 2://EWeek
                            try
                            {
                                int dayOfTheWeekEvent,dayOfTheWeekCurrent;
                                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                                String dateStr = ""+l.data.get(i).getYear();
                                if((l.data.get(i).getMonth()+1)<=9)
                                    dateStr = dateStr + "0" + (l.data.get(i).getMonth()+1);
                                else
                                    dateStr = dateStr + (l.data.get(i).getMonth()+1);
                                if((l.data.get(i).getDay())<=9)
                                    dateStr = dateStr + "0" + (l.data.get(i).getDay());
                                else
                                    dateStr = dateStr + (l.data.get(i).getDay());
                                Log.i(TAG, "BEFORE PARSE " + dateStr);
                                Date date = df.parse(dateStr);
                                Log.i(TAG, "AFTER PARSE");
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                dayOfTheWeekEvent = c.get(Calendar.DAY_OF_WEEK);

                                dateStr = ""+y;
                                if((m+1)<=9)
                                    dateStr = dateStr + "0" + (m+1);
                                else
                                    dateStr = dateStr + (m+1);
                                if(d<=9)
                                    dateStr = dateStr + "0" + d;
                                else
                                    dateStr = dateStr + d;
                                date = df.parse(dateStr);
                                c.setTime(date);
                                dayOfTheWeekCurrent = c.get(Calendar.DAY_OF_WEEK);
                                Log.i(TAG, ""+(dayOfTheWeekEvent == dayOfTheWeekCurrent));
                                if(dayOfTheWeekEvent == dayOfTheWeekCurrent)
                                {
                                    adapter.add(l.data.get(i));
                                }
                            }
                            catch(Exception e) {}
                            break;
                        case 3://EMonth
                            if(l.data.get(i).getDay()==d)
                                adapter.add(l.data.get(i));
                            break;
                        case 4://EYear
                            if(l.data.get(i).getDay()==d && l.data.get(i).getMonth()==m)
                                adapter.add(l.data.get(i));
                            break;
                    }
                }
            }
        adapter.sort(new Comparator<EventClass>() {
            @Override
            public int compare(EventClass lhs, EventClass rhs) {

                  if(lhs.getHours()*60+lhs.getMinutes()<rhs.getHours()*60+rhs.getMinutes())
                      return -1;
                  else if(lhs.getHours()*60+lhs.getMinutes()==rhs.getHours()*60+rhs.getMinutes())
                      return 0;
                  return 1;
            }
        });
            setListAdapter(adapter);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), EditEventActivity.class);
        EventClass E =(EventClass)getListAdapter().getItem(position);
        LISTDATA data = new LISTDATA();
        for (int m = 0; m<data.data.size();++m)
        {
            if(data.data.get(m)==l.getAdapter().getItem(position))
            {
                Log.i(TAG,"position "+ m);
                intent.putExtra("position",m);
            }
        }
        getActivity().startActivityForResult(intent,2);
        Log.i(TAG,"position !!!!!!");
    }

}




