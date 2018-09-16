package com.example.mi.prog.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.mi.prog.R;

import static android.content.ContentValues.TAG;

//Although this class has a weird name, it displays a Calendar and sets up OnDateClick events and other stuff
//Should have called it CalendarFragment
public class testfragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public testfragment() {}

    public static testfragment newInstance(String param1, String param2) {
        testfragment fragment = new testfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testfragment, container, false);
        CalendarView calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth)
            {
                Fragment testfragment3 = new ListClass();
                Bundle transfer = new Bundle();
                transfer.putInt("year", year);
                Log.i(TAG, Integer.toString(year));
                Log.i(TAG, Integer.toString(month));
                Log.i(TAG, Integer.toString(dayOfMonth));
                transfer.putInt("month",month);
                transfer.putInt("day",dayOfMonth);
                testfragment3.setArguments(transfer);
                // Handle navigation view item clicks here.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, testfragment3);
                fragmentTransaction.commit();
            }
        });
        return view;

    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //This thing has to be implemented in Activities that use this fragment
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
