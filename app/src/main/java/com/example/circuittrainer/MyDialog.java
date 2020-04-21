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

public class MyDialog extends AppCompatDialogFragment {

    public interface DialogListener{
        void getDialogInfo(IntervalUnit unit);
    }


    private EditText _name;
    private EditText _time;
    private DialogListener listener;
    String type;                            //  whether we're creating a rest or exercise dialog

    MyDialog(String _type){
        type = _type;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;            //  assign listener once attached
        } catch (ClassCastException e) {                                     //  ensure activity implements the interface
            Log.e("MyDialog", "Activity does not implement DialogListener!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());       //  builder to build the alert dialog box
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(v)
                .setTitle(type)
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
                        int time = Integer.parseInt(_time.getText().toString());
                        if (type.equals("Exercise")) {
                            String name = _name.getText().toString();
                            listener.getDialogInfo(new Exercise(name, time));
                        }
                        else{
                            listener.getDialogInfo(new Rest(time));
                        }
                    }
                });


        //  initialize the edit texts in the dialog box
        _name = (EditText) v.findViewById(R.id._name);
        _time = (EditText) v.findViewById(R.id._time);

        //  if entering a rest unit, set the name to be "Rest" and not editable
        if (type.equals("Rest")){
            _name.setText("Rest");
            _name.setFocusable(false);
        }

        return builder.create();            //  return the dialog box
    }


}
