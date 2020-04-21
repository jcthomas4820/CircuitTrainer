/*
* Editor for a user to create a new custom circuit
* */

package com.example.circuittrainer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class CircuitActivity extends AppCompatActivity implements MyDialog.DialogListener {

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

    ArrayList<String> namesQueue;                //  queue will contain user entered circuit exercises
    ArrayList<Integer> timeQueue;                   //  queue will contain user entered circuit times
    ArrayList<String> listItems;                    //  list items in exerciseList
    ArrayAdapter<String> adapter;                   //  array adapter to put items in exerciseList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_layout);

        //  get references to UI elements
        namesQueue = new ArrayList<>();
        timeQueue = new ArrayList<>();
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

        //intervalQueue = new ArrayList<>();
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
                openDialog("Exercise");
            }
        });

        //  open the dialog box for adding a rest interval
        add_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Rest");
            }
        });

        //  begin TimerActivity
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CircuitActivity.this, TimerActivity.class);

                //  fill intent with exercise list, sets, and warning
                i.putExtra("namesQueue", namesQueue);
                i.putExtra("timeQueue", timeQueue);
                i.putExtra("circuitList", listItems);
                i.putExtra("sets", sets);
                i.putExtra("warning", warning);

                startActivity(i);
            }
        });

    }

    //  open the exercise dialog for user to add an exercise
    public void openDialog(String type){
        MyDialog dialog = new MyDialog(type);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }


    @Override
    public void getDialogInfo(IntervalUnit unit) {
        //  will receive the interval unit that user creates
        adapter.add(unit.getStringDisplay());                   //  add it to the exercise ListView
        namesQueue.add(unit.name);                                //  add to both queues
        timeQueue.add(unit.time);
    }
}
