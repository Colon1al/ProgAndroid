package com.example.mi.prog;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapterForEvents extends ArrayAdapter<EventClass>
{
    ArrayList<EventClass> LST;
    public AdapterForEvents(Context context, ArrayList<EventClass> list)
    {
        super(context, 0 , list);
        LST = list;

    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            EventClass Event = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_event, parent, false);
            }
            // Lookup view for data population

            TextView tvName = (TextView) convertView.findViewById(R.id.textView_Name);
            TextView tvInfo = (TextView) convertView.findViewById(R.id.textView_Info);
            TextView Time = (TextView) convertView.findViewById(R.id.textView_Time);
            // Populate the data into the template view using the data object
            tvName.setText(Event.getName());
            tvInfo.setText(Event.getInfo());
            Time.setText(Event.getTime());
            // Return the completed view to render on screen
            ArgbEvaluator A = new ArgbEvaluator();
            float F = position/(float)LST.size();
            if (LST.size()<8)
                F = position*0.125f;
            int i = (Integer)A.evaluate(F, 0xffffffff, 0xffFFFE36);
            String s = Integer.toHexString(i);
            convertView.findViewById(R.id.RelativeEventLayout).setBackgroundColor(i);
            Log.i(TAG,s+" COLOUR on POS :"+position +"          "+ LST.size() +"   "+ F );
            return convertView;


        }
}



