package com.example.mi.prog;

import android.content.Context;
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
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class PersonInfo {
    File file;
    private final static Gson GSON = new GsonBuilder().create();
    public void writeFile(Context context)
    {
        try
        {
            Vault vault =new Vault();
            FileOutputStream fi = context.openFileOutput("UserInfo.data",MODE_PRIVATE);
            //vault.jsonHandicapWrite();
            String json = GSON.toJson(vault,Vault.class);
            Log.i(TAG, "JSON!!!!!!!!!!!:"+json);
            fi.write(json.getBytes());
            fi.close();

        }
        catch(Exception e)
        {
            Log.i("MYAPP", "exception",e);
        }

    }
    public void readFile(Context context)
    {
        try
        {
            FileInputStream f = context.openFileInput("UserInfo.data");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(f)));
            String json = reader.readLine();
            Vault vault = GSON.fromJson(json, new TypeToken<Vault>(){}.getType());
            //vault.jsonHandicapRead();
            f.close();
        }
        catch(Exception e)
        {Log.i(TAG, "READ JSON USER INFO ERROR"+ e);}

    }
}
