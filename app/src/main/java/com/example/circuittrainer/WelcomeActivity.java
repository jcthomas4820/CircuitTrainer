/*
* Welcome Screen the User interacts with once first opening the App
*   User can select previously saved circuits, or build a new one
* */

package com.example.circuittrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    //  declare references to buttons
    Button newButton;
    Button savedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        //  assign references to buttons
        newButton = (Button) findViewById(R.id.new_button);
        savedButton = (Button) findViewById(R.id.saved_button);

        //  new opens an editor to build a circuit
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  open ExerciseActivity
                startActivity(new Intent(WelcomeActivity.this, CircuitActivity.class));
                finish();
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  open SavedActivity
                startActivity(new Intent(WelcomeActivity.this, SavedActivity.class));
                finish();
            }
        });


    }
}
