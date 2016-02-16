package io.barryliu.newspager;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

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
        handler.postDelayed(r, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);



    }

    public void jumpToNext(){
        Intent intent =new Intent(this,GuideActivity.class);
        startActivity(intent);
    }


}
