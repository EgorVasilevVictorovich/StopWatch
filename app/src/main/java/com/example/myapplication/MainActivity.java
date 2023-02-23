package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {


    private Button buttonStart;
    private Button buttonPause;
    private Button buttonRevers;
    private TextView stopwatchOut;


    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timePause = 0L;
    private long updatedTime = 0L;
    private boolean timeMod;


    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonRevers = findViewById(R.id.buttonRevers);
        stopwatchOut = findViewById(R.id.stopwatchOut);


        buttonStart.setOnClickListener(listener);
        buttonPause.setOnClickListener(listener);
        buttonRevers.setOnClickListener(listener);
    }


    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {



            switch (view.getId()) {
                case R.id.buttonStart:
                    timeMod = true;
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);
                    break;
                case R.id.buttonPause:
                    timePause = 0L;
                    timePause += timeInMilliseconds;
                    handler.removeCallbacks(updateTimerThread);
                    break;
                case R.id.buttonRevers:
                    timeMod = false;
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);

                    break;
            }
        }
    };


    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (timeMod) {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timePause + timeInMilliseconds;
            }else{
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timePause - timeInMilliseconds;
            }
            int milliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;
            int day = hour / 24;

            second = second % 60;
            minute = minute % 60;
            hour = hour % 24;


            stopwatchOut.setText("" + day + ":" + hour + ":" + minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}