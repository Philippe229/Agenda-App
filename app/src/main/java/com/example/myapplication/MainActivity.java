package com.example.myapplication;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.app.NotificationManager;
import android.app.PendingIntent;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {


    private CalendarView calendar;
    private Button toDoListButton;
    private Button PriorityListButton;

    private int dayOfMonth;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = (CalendarView) findViewById(R.id.calendar);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int yearArg, int monthArg, int dayOfMonthArg) {
                dayOfMonth = dayOfMonthArg;
                month = monthArg + 1;
                year = yearArg;
                Toast.makeText(getApplicationContext(), dayOfMonthArg + "/" + monthArg + "/" + yearArg, Toast.LENGTH_LONG).show();
            }
        });

        toDoListButton = (Button) findViewById(R.id.toDoListButton);
        toDoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ToDoList.class);
                intent.putExtra("dayOfMonth", dayOfMonth);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });

        PriorityListButton = (Button) findViewById(R.id.PriorityList);
        PriorityListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PriorityList.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    public void setAlarm(View v) {

        Long alertTime = new GregorianCalendar().getTimeInMillis() + 5*1000;

        Intent alertIntent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }






}
