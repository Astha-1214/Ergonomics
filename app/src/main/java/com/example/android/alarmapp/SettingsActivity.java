package com.example.android.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int repeatingInterval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner exerciseSpinner = findViewById(R.id.exerciseTime);
        Spinner waterSpinner = findViewById(R.id.waterTime);

        exerciseSpinner.setOnItemSelectedListener(this);
        waterSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> Time = new ArrayList<String>();
        Time.add("Every 2 minutes");
        Time.add("Every 40 minutes");
        Time.add("Every 60 minutes");
        Time.add("Every 90 minutes");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Time);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        exerciseSpinner.setAdapter(dataAdapter);
        waterSpinner.setAdapter(dataAdapter);

        Button Alarm=findViewById(R.id.Alarm);
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                setTimer();
            }
        });

    }

    String exercise;
    String water;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        Spinner spin=(Spinner)parent;
        Spinner spin2=(Spinner)parent;

        if(spin.getId()==R.id.exerciseTime)
        {
            exercise = parent.getItemAtPosition(pos).toString();
            if(exercise.equals("Every 2 minutes"))
                repeatingInterval=1;
            else if(exercise.equals("Every 40 minutes"))
                repeatingInterval=40;
            else if(exercise.equals("Every 60 minutes"))
                repeatingInterval=60;
            else
                repeatingInterval=90;


//            Toast toast=Toast.makeText(this,exercise,Toast.LENGTH_LONG);
//            toast.show();
        }
        else if(spin2.getId()==R.id.waterTime)
        {
            water=parent.getItemAtPosition(pos).toString();
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public void setTimer()
    {
        Toast toast=Toast.makeText(SettingsActivity.this,"ALARM SET",Toast.LENGTH_LONG);
        toast.show();

        int startHour=MainActivity.getStartHour();
        int startMin=MainActivity.getStartMin();
        int endHour=MainActivity.getEndHour();
        int endMin=MainActivity.getEndMin();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_start.setTime(date);
        cal_end.setTime(date);

        cal_start.set(Calendar.HOUR_OF_DAY, startHour);
        cal_start.set(Calendar.MINUTE, startMin);
        cal_start.set(Calendar.SECOND, 0);

        cal_end.set(Calendar.HOUR_OF_DAY,endHour);
        cal_end.set(Calendar.MINUTE, endMin);
        cal_end.set(Calendar.SECOND, 0);

        Intent i = new Intent(SettingsActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 24444, i, 0);

        i.putExtra("end", cal_end.getTimeInMillis());
        //i.putExtra("pI",pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,cal_now.getTimeInMillis(),pendingIntent);

        long time=cal_start.getTimeInMillis();
        long interval=repeatingInterval*60*1000;

//        Log.e("++++ASTHA++++", "here");
//        Log.e("end time: ", cal_end.getTimeInMillis()+"");
//        Log.e("system time: ", System.currentTimeMillis()+"");
        if(System.currentTimeMillis()>=cal_end.getTimeInMillis())
        {
            alarmManager.cancel(pendingIntent);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,interval, pendingIntent);

    }


}
