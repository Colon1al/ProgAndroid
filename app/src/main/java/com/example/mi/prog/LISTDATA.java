package com.example.mi.prog;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
//This class is responsible for dealing with loaded EventClass events (adding/deleting, loading/writing)
public class LISTDATA {
    public static List<EventClass> data = new ArrayList<EventClass>();
    private final static Gson GSON = new GsonBuilder().create();
    File file;
    private static int lmonth;
    private static int lyear;

    public LISTDATA() {}

    public void writeFile(Context context) {
        try {
            FileOutputStream fi = context.openFileOutput(Integer.toString(lyear) + "-" + Integer.toString(lmonth) + ".data", MODE_PRIVATE);
            String json = GSON.toJson(data);
            fi.write(json.getBytes());
            fi.close();


        } catch (Exception e) {
            Log.i(TAG, "LISTDATA - writeFile - exception" + e);
        }
    }

    public void readFile(Context context) {
        try {
            File file;
            FileInputStream f;
            BufferedReader reader;
            String json;

            data = new ArrayList<>();
            file = new File(context.getFilesDir()+"/" + Integer.toString(lyear) + "-" + Integer.toString(lmonth) + ".data");
            if (file.getAbsoluteFile().exists())
            {
                f = context.openFileInput(Integer.toString(lyear) + "-" + Integer.toString(lmonth) + ".data");
                reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
                json = reader.readLine();
                data = GSON.fromJson(json, new TypeToken<List<EventClass>>() {
                }.getType());
                f.close();
            }

            //Repeating events
            ArrayList<EventClass> temp = new ArrayList<>();
            //EDay
            file = new File(context.getFilesDir()+"/EDay.data");
            Log.i(TAG,"LISTDATA, readfile, Eday exist "+ file.getAbsolutePath());
            if (file.getAbsoluteFile().exists())
            {
                FileInputStream fday = context.openFileInput("EDay.data");
                reader = new BufferedReader(new InputStreamReader(new DataInputStream(fday)));
                json = reader.readLine();
                temp = GSON.fromJson(json, new TypeToken<List<EventClass>>() {
                }.getType());
                Log.i(TAG,"EDay temp size = "+temp.size());
                fday.close();
                for (int i = 0; i < temp.size(); i++)
                    data.add(temp.get(i));
            }
            //EWeek
            file = new File(context.getFilesDir()+"/EWeek.data");
            if (file.getAbsoluteFile().exists())
            {
                f = context.openFileInput("EWeek.data");
                reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
                json = reader.readLine();
                temp = GSON.fromJson(json, new TypeToken<List<EventClass>>() {
                }.getType());
                f.close();
                for (int i = 0; i < temp.size(); i++)
                    data.add(temp.get(i));
            }
            //EMonth
            file = new File(context.getFilesDir()+"/EMonth.data");
            if (file.getAbsoluteFile().exists())
            {
                f = context.openFileInput("EMonth.data");
                reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
                json = reader.readLine();
                temp = GSON.fromJson(json, new TypeToken<List<EventClass>>() {
                }.getType());
                f.close();
                for (int i = 0; i < temp.size(); i++)
                    data.add(temp.get(i));
            }
            //EYear
            file = new File(context.getFilesDir()+"/EYear.data");
            if (file.getAbsoluteFile().exists())
            {
                f = context.openFileInput("EYear.data");
                reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
                json = reader.readLine();
                //TODO Template example
                temp = GSON.fromJson(json, new TypeToken<List<EventClass>>() {
                }.getType());
                f.close();
                for (int i = 0; i < temp.size(); i++)
                    if(temp.get(i).getMonth() == lmonth)
                        data.add(temp.get(i));
            }

        } catch (Exception e) {
            Log.i(TAG, "LISTDATA - readFile - exception" + e);
        }

    }

    public void add(Context context, EventClass newEvent) {
        try {
            if (newEvent != null) {
                if (newEvent.getRepeat() != 0) {
                    data.add(newEvent);
                    writeRepetive(context, newEvent);
                }
                else {
                    Log.i(TAG, "!!!!!!INTERNAL LOG : " + Integer.toString(lmonth) + " : " + Integer.toString(lyear));
                    Log.i(TAG, "!!!!!!EVENT LOG : " + Integer.toString(newEvent.getMonth()) + " : " + Integer.toString(newEvent.getYear()));
                    if (newEvent.getMonth() == lmonth && newEvent.getYear() == lyear) {
                        data.add(newEvent);
                    }

                    File f = new File("/data/data/com.example.mi.prog/files/" + Integer.toString(newEvent.getYear()) + "-" + Integer.toString(newEvent.getMonth()) + ".data");
                    Log.i(TAG, "LOG FILENAME!!!!:" + f.getAbsolutePath());
                    if (!f.getAbsoluteFile().exists()) {
                        Log.i(TAG, "LOG FILENAME!!!!:" + f.getName());
                        Log.i(TAG, "!!!!!! LOG DATATMP SIZE: NOT A LOG ");
                        ArrayList<EventClass> datatmp = new ArrayList<>();
                        datatmp.add(newEvent);
                        writeFileSolo(context, f.getName(), datatmp);
                    } else {
                        Log.i(TAG, "!!!!!! LOG DATATMP SIZE: no ");
                        ArrayList<EventClass> datatmp = readFileSolo(context, f.getName());
                        Log.i(TAG, "!!!!!! LOG DATATMP SIZE: " + datatmp.size());
                        datatmp.add(newEvent);
                        writeFileSolo(context, f.getName(), datatmp);
                    }

                }
            }


        } catch (Exception e) {
            Log.i(TAG, "LISTDATA - add - exception", e);
        }

    }

    public void setLmonth(int lmonth) {
        this.lmonth = lmonth;
    }

    public void setLyear(int lyear) {
        this.lyear = lyear;
    }

    private ArrayList<EventClass> readFileSolo(Context context, String fileName) {
        try {
            Log.i(TAG, "Before file open to stream: filename :"+fileName);
            FileInputStream f = context.openFileInput(fileName);
            Log.i(TAG, "SOLO READ : filename :"+fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
            Log.i(TAG, "Breader : filename :"+fileName);
            String json = reader.readLine();
            Log.i(TAG, "JSON : filename :"+fileName);
            ArrayList<EventClass> datatmp = GSON.fromJson(json, new TypeToken<ArrayList<EventClass>>() {
            }.getType());
            Log.i(TAG, "Arrlist get type : filename :"+fileName);
            f.close();

            return datatmp;
        } catch (Exception e) {
            Log.i(TAG, "LISTDATA - readFileSolo -  filename :"+fileName +", exception: "+e );
            return null;
        }

    }

    private void writeFileSolo(Context context, String fileName, ArrayList<EventClass> E) {
        try {

            FileOutputStream fi = context.openFileOutput(fileName, MODE_PRIVATE);
            String json = GSON.toJson(E);
            fi.write(json.getBytes());
            fi.close();


        } catch (Exception e) {
            Log.i(TAG,"LISTDATA - writeFileSolo - exception=" + e);
        }

    }
    //poor english :(
    public void writeRepetive(Context context, EventClass E) {
        try {
            String fileName = "0";

            switch (E.getRepeat()) {
                case 1:
                    fileName = "EDay.data";
                    break;
                case 2:
                    fileName = "EWeek.data";
                    break;
                case 3:
                    fileName = "EMonth.data";
                    break;
                case 4:
                    fileName = "EYear.data";
                    break;
            }
            File f = new File("/data/data/com.example.mi.prog/files/"+fileName);
            Log.i(TAG,f.getAbsolutePath() + f.getAbsoluteFile().exists());
            if (!(f.getAbsoluteFile().exists())) {
                Log.i(TAG, "LOG FILENAME!!!!:" + f.getName());
                Log.i(TAG, "!!!!!! LOG DATATMP SIZE: NOT A LOG ");
                ArrayList<EventClass> datatmp = new ArrayList<>();

                datatmp.add(E);
                writeFileSolo(context, f.getName(), datatmp);
            } else {
                Log.i(TAG, "!!!!!! LOG DATATMP SIZE: no ");
                ArrayList<EventClass> datatmp = readFileSolo(context, fileName);
                datatmp.add(E);
                writeFileSolo(context, f.getName(), datatmp);
            }



        } catch (Exception e) {
            Log.i(TAG, "LISTDATA - writeRepetive - exception=", e);
        }
    }

    public void deleteRepetition(Context context, EventClass E) {
        try {

            int i=0;
            String fileName = "0";
            switch (E.getRepeat()) {
                case 1:
                    fileName = "EDay.data";
                    break;
                case 2:
                    fileName = "EWeek.data";
                    break;
                case 3:
                    fileName = "EMonth.data";
                    break;
                case 4:
                    fileName = "EYear.data";
                    break;
            }
            File f = new File("/data/data/com.example.mi.prog/files/"+fileName);
            Log.i(TAG, "!!!!!! LOG DATATMP SIZE: no ");
            ArrayList<EventClass> datatmp = readFileSolo(context, f.getName());
            Log.i(TAG, "!!!!!! LOG DATATMP SIZE: " + datatmp.size());
            for(;i<datatmp.size();++i)
            {
                Log.i(TAG,"DELETE REP  "+E.isEqual(datatmp.get(i)));
                if (E.isEqual(datatmp.get(i)))
                {
                    E.setRepeat(0);
                    datatmp.remove(i);
                    break;
                }
            }
            writeFileSolo(context, f.getName(), datatmp);
        } catch (Exception e){
            Log.i(TAG,"LISTDATA - deleteRepetition - exception=" + e);
        }
    }
}