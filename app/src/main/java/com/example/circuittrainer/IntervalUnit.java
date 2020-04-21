/*
* Acts as Unit Template when building a custom circuit
* */

package com.example.circuittrainer;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class IntervalUnit {

    String name;
    String color;
    int time;

    //  fill the screen with color
    void fillColor(){

    }

    String getStringDisplay(){
        return name+": "+time+"sec";
    }


}
