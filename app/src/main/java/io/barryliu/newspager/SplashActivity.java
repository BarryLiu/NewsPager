package io.barryliu.newspager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.barryliu.newspager.lisenter.OnJumpLisenter;
import io.barryliu.newspager.view.Contacts;

public class SplashActivity extends AppCompatActivity{

    Handler handler =new Handler();
    Runnable r = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

          r = new Runnable() {
            @Override
            public void run() {
                jumpToNext();
                finish();
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
    }

    public void jumpToNext(){

        Intent intent =null;

        SharedPreferences preferences = getSharedPreferences(Contacts.first, MODE_PRIVATE);
        boolean flag = preferences.getBoolean(Contacts.TagIsFirst,false);
        if(flag){
            intent = new Intent(this,MainActivity.class);
        }else{
            intent = new Intent(this,GuideActivity.class);
        }

        startActivity(intent);


    }



}
