/*
* Editor for a user to create a new custom circuit
* */

package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CircuitActivity extends AppCompatActivity {

    ListView builderList;
    Button saveButton;
    Button startButton;
    ArrayAdapter<String> arrayAdapter;
    String circuit_builder_items[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_activity);

        builderList = (ListView) findViewById(R.id.builder_list);
        saveButton = (Button) findViewById(R.id.save_button);
        startButton = (Button) findViewById(R.id.start_button);
        circuit_builder_items = getResources().getStringArray(R.array.circuit_builder_items);

        //  define adapter to populate the ListView with
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, circuit_builder_items);

        //  populate ListView with the appropriate items
        builderList.setAdapter(arrayAdapter);
        
    }
}
