package com.example.mi.prog;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DialogActivity extends AppCompatActivity {

    String good,bad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        searchLink();
    }



    public void toGo(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://kudago.com/"));
        startActivity(intent);
    }

    public void goodMusic(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://music.yandex.ru/search?text="+good));
        startActivity(intent);
    }

    public void badMusic(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://music.yandex.ru/search?text="+bad));
        startActivity(intent);
    }

    public void vines(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/vines/"));
        startActivity(intent);
    }

    public void leave(View v)
    {
        Intent intent = new Intent(this, BurgerActivity.class);
        finish();
    }

    void searchLink()
    {
        Vault v = new Vault();
        good = v.musicWithGoodMood;
        bad = v.musicWithBadMood;

        for (int i=0;i<good.length();i++)
        {
            if(good.charAt(i)!=' ')
            {
                good = good.substring(i);
                break;
            }
        }
        for (int i=0;i<good.length();i++)
        {
            if(good.charAt(i)==' ')
            {
                good = good.substring(0,i) + "%20" +good.substring(i+1);
            }
            if(good.charAt(i)==',')
            {
                good = good.substring(0,i) + "%2C" +good.substring(i+1);
            }
        }

        for (int i=0;i<bad.length();i++)
        {
            if(bad.charAt(i)!=' ')
            {
                bad = bad.substring(i);
                break;
            }
        }
        for (int i=0;i<bad.length();i++)
        {
            if(bad.charAt(i)==' ')
            {
                bad = bad.substring(0,i) + "%20" +bad.substring(i+1);
            }
            if(bad.charAt(i)==',')
            {
                bad = bad.substring(0,i) + "%2C" +bad.substring(i+1);
            }
        }
    }
}
