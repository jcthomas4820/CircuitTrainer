//  https://www.youtube.com/watch?v=ARezg1D9Zd0


package com.example.circuittrainer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExerciseDialog extends AppCompatDialogFragment {

    public interface ExerciseDialogListener{
        void getExerciseInfo(String exerciseName, int time);
    }

    private EditText exercise_name;
    private EditText rest_time;
    private ExerciseDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExerciseDialogListener) context;            //  assign listener once attached
        } catch (ClassCastException e) {                                     //  ensure activity implements the interface
            Log.e("ExerciseDialog", "Activity does not implement ExerciseDialogListener!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());       //  builder to build the alert dialog box
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.exercise_dialog, null);

        builder.setView(v)
                .setTitle("Exercise")
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  nothing will happen if user goes back...
                    }
                })
                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  pull the text, pass to the activity
                        String exercise = exercise_name.getText().toString();
                        int time = Integer.parseInt(rest_time.getText().toString());

                        listener.getExerciseInfo(exercise, time);
                    }
                });

        //  initialize the edit texts in the dialog box
        exercise_name = (EditText) v.findViewById(R.id.exercise_name);
        rest_time = (EditText) v.findViewById(R.id.rest_time);

        return builder.create();            //  return the dialog box
    }


}
