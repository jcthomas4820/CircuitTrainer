package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    ArrayList<String> namesQueue;                //  queue will contain user entered circuit exercises
    ArrayList<Integer> timesQueue;                   //  queue will contain user entered circuit times
    ArrayAdapter<String> adapter;               //  will place namesQueue inside list view
    ListView timerList;                         //  contains names of exercises/rest
    TextView numSetsText;                       //  displays the number of sets
    int sets;
    int warning;
    int currSet;                                //  set user is currently on

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
        currSet = 1;

        timerList = (ListView) findViewById(R.id.timer_list);
        numSetsText = (TextView) findViewById(R.id.set_text);

        adapter = new ArrayAdapter<String>(this, R.layout.interval_listitem_layout, namesQueue);
        timerList.setAdapter(adapter);

        numSetsText.setText("Sets: " + currSet + "/" + sets);           //  display 1/sets




    }
}
