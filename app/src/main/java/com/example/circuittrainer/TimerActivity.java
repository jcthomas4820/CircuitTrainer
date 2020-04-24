//  https://www.youtube.com/watch?v=zmjfAcnosS0&t=107s

package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    TextView timer_text;
    Button pause_play_button;

    CountDownTimer timer;
    long timeLeft = 600000;                                  //  time left in ms
    boolean running;


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
        timer_text = (TextView) findViewById(R.id.timer_display);
        pause_play_button = (Button) findViewById(R.id.pause_button);

        adapter = new ArrayAdapter<String>(this, R.layout.interval_listitem_layout, namesQueue);
        timerList.setAdapter(adapter);

        numSetsText.setText("Sets: " + currSet + "/" + sets);           //  display 1/sets

        //  will either pause or play the timer
        pause_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTimer();
            }
        });

        updateTimerText();              //  initialize the timer text
        playTimer();                    //  begin the timer
    }

    //  function toggles timer between off and on states
    void toggleTimer(){
        if (running){
            pauseTimer();
        }
        else{
            playTimer();
        }
    }

    void playTimer(){

        //  assign new CountDownTimer
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;           //  keep time up to date once every one second passes
                updateTimerText();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        running = true;
        pause_play_button.setText("PAUSE");         //  flip the pause/play button
    }

    void pauseTimer(){
        timer.cancel();
        running = false;
        pause_play_button.setText("PLAY");         //  flip the pause/play button
    }

    //  update displayed text for timer
    void updateTimerText(){

        //  get min and secs left on timer
        int min = (int) timeLeft/60000;
        int sec = (int) timeLeft % 60000 / 1000;

        //  build string to display
        String timeString;

        timeString = "" + min + ": ";
        //  always display seconds with 2 digits
        if (sec < 10){
            timeString = timeString + "0" + sec;
        }
        else{
            timeString = timeString + sec;
        }

        timer_text.setText(timeString);

    }

}
