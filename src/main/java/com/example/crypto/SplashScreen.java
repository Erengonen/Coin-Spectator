package com.example.crypto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

public class SplashScreen extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent git= new Intent (SplashScreen.this,MainActivity.class);
                startActivity(git);



            }
        },2000);
    }
}
