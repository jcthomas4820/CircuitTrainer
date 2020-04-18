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

import java.util.HashMap;

public class CircuitActivity extends AppCompatActivity {

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

        //  set initial values for sets and warnings
        sets = 1;
        warning = 0;
        num_sets.setText(""+sets);
        num_warning.setText(""+warning);


    }





}
