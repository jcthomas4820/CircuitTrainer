package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    ArrayList<String> namesQueue;                //  queue will contain user entered circuit exercises
    ArrayList<Integer> timesQueue;                   //  queue will contain user entered circuit times
    int sets;
    int warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        //  assign the timer's variables
        Intent i = getIntent();
        sets = i.getIntExtra("sets", 1);
        warning = i.getIntExtra("warning", 0);
        namesQueue = i.getStringArrayListExtra("namesQueue");
        timesQueue = i.getIntegerArrayListExtra("timeQueue");

        int x=1;
    }
}
