/*
* Editor for a user to create a new custom circuit
* */

package com.example.circuittrainer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CircuitActivity extends AppCompatActivity implements ExerciseDialog.ExerciseDialogListener {

    ListView exerciseList;
    Button add_exercise;
    Button add_rest;
    Button add_set;
    Button sub_set;
    Button add_warning;
    Button sub_warning;
    Button save_button;
    Button start_button;
    TextView num_sets;
    TextView num_warning;
    int sets;
    int warning;

    ArrayList<IntervalUnit> intervalQueue;          //  queue of the user's entered circuit
    ArrayList<String> listItems;                    //  list items in exerciseList
    ArrayAdapter<String> adapter;                   //  array adapter to put items in exerciseList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_layout);

        //  get references to UI elements
        exerciseList = (ListView) findViewById(R.id.exercise_list);
        add_exercise = (Button) findViewById(R.id.add_exercise);
        add_rest = (Button) findViewById(R.id.add_rest);
        add_set = (Button) findViewById(R.id.add_set);
        sub_set = (Button) findViewById(R.id.sub_set);
        add_warning = (Button) findViewById(R.id.add_warning);
        sub_warning = (Button) findViewById(R.id.sub_warning);
        save_button = (Button) findViewById(R.id.save_button);
        start_button = (Button) findViewById(R.id.start_button);
        num_sets = (TextView) findViewById(R.id.num_sets);
        num_warning = (TextView) findViewById(R.id.num_warning);

        intervalQueue = new ArrayList<IntervalUnit>();
        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.interval_listitem_layout, listItems);
        exerciseList.setAdapter(adapter);

        //  set initial values for sets and warnings
        sets = 1;
        warning = 0;
        num_sets.setText(""+sets);
        num_warning.setText(""+warning);


        //  increment set count
        add_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sets = sets+1;
                num_sets.setText(""+sets);
            }
        });

        //  decrement set count
        sub_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sets == 1){                 //  avoid <=0 sets
                    return;
                }
                sets = sets-1;
                num_sets.setText(""+sets);
            }
        });

        //  increment warning count
        add_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning = warning+1;
                num_warning.setText(""+warning);
            }
        });

        //  decrement warning count
        sub_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (warning == 0){                 //  avoid negative warning
                    return;
                }
                warning = warning-1;
                num_warning.setText(""+warning);
            }
        });

        //  open the dialog box for adding an exercise
        add_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseDialog();
            }
        });

        //  open the dialog box for adding a rest interval
        add_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestDialog();
            }
        });

    }

    //  open the exercise dialog for user to add an exercise
    public void openExerciseDialog(){
        ExerciseDialog exDialog = new ExerciseDialog();
        exDialog.show(getSupportFragmentManager(), "exercise dialog");
    }

    //  open the rest dialog for user to add a rest interval
    public void openRestDialog(){

    }


    @Override
    public void getExerciseInfo(String exerciseName, int time) {
        //  will receive the exerciseName and time from fragment, which is exercise dialog box

        //Log.i("Main Activity", "EXERCISE: " + exerciseName);
        //Log.i("Main Activity", "REST: " + time);

        Exercise exercise = new Exercise(exerciseName, time);       //  create new exercise interval unit
        adapter.add(exercise.getStringDisplay());                   //  add it to the exercise ListView
        intervalQueue.add(exercise);                                //  add to the queue
    }
}
