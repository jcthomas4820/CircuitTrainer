//  https://www.youtube.com/watch?v=zmjfAcnosS0&t=107s
//  https://stackoverflow.com/questions/29509010/how-to-play-a-short-beep-to-android-phones-loudspeaker-programmatically
//  https://www.youtube.com/watch?v=DoYnz0GYN1w

package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;

import android.content.Intent;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    ArrayList<String> namesQueue;                //  queue will contain user entered circuit exercises
    ArrayList<Integer> timesQueue;                   //  queue will contain user entered circuit times
    ArrayList<String> namesQueue_orig;           // original version of namesQueue
    ArrayList<Integer> timesQueue_orig;          //  original version of timesQueue
    ArrayAdapter<String> adapter;               //  will place namesQueue inside list view
    ListView timerList;                         //  contains names of exercises/rest
    TextView numSetsText;                       //  displays the number of sets
    TextView current_exercise_text;             //  displays the current exercise on timer
    int sets;
    int warning;
    int currSet;                                //  set user is currently on
    TextView timer_text;
    Button pause_play_button;

    CountDownTimer timer;
    long timeLeft = 10000;                                  //  time left in ms
    boolean running;

    ToneGenerator toneGen;                     //  used to play beep
    TextToSpeech ttS;                           //  for speaking warning

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        //  assign the timer's variables
        Intent i = getIntent();
        sets = i.getIntExtra("sets", 1);
        namesQueue_orig = i.getStringArrayListExtra("namesQueue");
        timesQueue_orig = i.getIntegerArrayListExtra("timeQueue");


        //  make copies of each queue
        namesQueue = new ArrayList<>();
        timesQueue = new ArrayList<>();
        makeCopy();

        currSet = 1;
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 1000);                //  set up beep generator
        ttS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = ttS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("ttS", "Error with Speech. Language not supported?");
                    }
                }
                else{
                    Log.e("ttS", "Error with Speech. Initialization failure");
                }
            }
        });

        timerList = (ListView) findViewById(R.id.timer_list);
        numSetsText = (TextView) findViewById(R.id.set_text);
        timer_text = (TextView) findViewById(R.id.timer_display);
        pause_play_button = (Button) findViewById(R.id.pause_button);
        current_exercise_text = (TextView) findViewById(R.id.current_exercise);

        adapter = new ArrayAdapter<String>(this, R.layout.interval_listitem_layout, namesQueue);
        timerList.setAdapter(adapter);

        numSetsText.setText("Set: " + currSet + "/" + sets);           //  display currSet/sets

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



    //  makes copy of original namesQueue and timesQueue to the list in the List View
    void makeCopy(){

        //  make copy of namesQueue_orig
        for (String name : namesQueue_orig){
            namesQueue.add(name);
        }

        //  make copy of timesQueue_orig
        for (int time : timesQueue_orig){
            timesQueue.add(time);
        }

        return;
    }


    //  function called every time timer stops i.e. exercise is over
        //  used to play name of exercise, then beep
    void playSpeech(){
        //  speech to say exercise name, beep

        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP,1000);
    }


    //  will start the next exercise from the queue
    void beginNextExercise(){

        //  get next element in names queue and remove
        String currEx = namesQueue.get(0);
        namesQueue.remove(0);
        adapter.notifyDataSetChanged();
        //  set the exercise text
        current_exercise_text.setText(currEx);
        //  get next element in times queue and remove, set time
        timeLeft = (long) timesQueue.get(0) * 1000;
        timesQueue.remove(0);

        playSpeech();

        playTimer();
    }


    //  will alert user when 10 seconds are left
    void playVoice(String speakText){

        ttS.speak(speakText, TextToSpeech.QUEUE_FLUSH, null, null);

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
            //  once timer is finished, set up the next exercise item
            public void onFinish() {
                //  if queue is empty
                if (namesQueue.isEmpty()) {

                    //  update number of current set
                    currSet++;

                    //  circuit is over, all sets complete
                    if(currSet > sets){
                        playSpeech();

                        //  set exercise text to be done!
                        current_exercise_text.setText("DONE!");
                    }
                    else{
                        makeCopy();
                        numSetsText.setText("Set: " + currSet + "/" + sets);           //  display currSet/sets
                        beginNextExercise();                                    //  start brand new set
                    }
                }
                else {
                    beginNextExercise();
                }
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

        //  say 10 second warning if 10 sec left
        if (sec == 10) {
            playVoice("ten");
        }

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

    @Override
    protected void onDestroy() {

        //  get rid of TextToSpeech
        if (ttS != null){
            ttS.stop();
            ttS.shutdown();
        }

        timer.cancel();         //  end the timer

        super.onDestroy();
    }

}
