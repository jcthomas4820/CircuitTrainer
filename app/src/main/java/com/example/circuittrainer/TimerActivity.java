package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

public class TimerActivity extends AppCompatActivity {

    ArrayList<IntervalUnit> intervalQueue;
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
        intervalQueue = (ArrayList<IntervalUnit>) i.getParcelableArrayListExtra("intervalQueue");

    }
}
