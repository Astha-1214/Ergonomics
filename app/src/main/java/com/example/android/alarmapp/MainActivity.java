package com.example.android.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TimePicker startTime;
    TimePicker endTime;
    static int startHour, startMin, endHour, endMin;

    TextView start;
    TextView end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTime = findViewById(R.id.startTime);


        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                startHour = i;
                startMin = i1;

                start = findViewById(R.id.start);
                start.setText("Start Time: " + startHour + ":" + startMin);
            }
        });

        endTime = findViewById(R.id.endTime);

        endTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                endHour = i;
                endMin = i1;

                end = findViewById(R.id.end);
                end.setText("End Time: " + endHour + ":" + endMin);
            }
        });


        Button button = findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

    }

    static int repeatingInterval;

    public static void getExerciseTime(String i) {
        if (i.equals("Every 2 minutes"))
            repeatingInterval = 1;
        else if (i.equals("Every 40 minutes"))
            repeatingInterval = 40;
        else if (i.equals("Every 60 minutes"))
            repeatingInterval = 60;
        else
            repeatingInterval = 90;
    }

    public static int getStartHour() {
        return startHour;
    }

    public static int getStartMin() {
        return startMin;
    }

    public static int getEndHour() {
        return endHour;
    }

    public static int getEndMin() {
        return endMin;
    }


}
