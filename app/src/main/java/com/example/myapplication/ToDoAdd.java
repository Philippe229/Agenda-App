package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ToDoAdd extends AppCompatActivity {

    private Spinner spinner;

    private Button saveNewToDoButton;
    private Button cancelNewToDoButton;

    private ArrayAdapter<CharSequence> adapter;

    private EditText nameText;
    private EditText dueDateText;
    private EditText timeNeededText;
    private EditText weightText;
    private EditText courseCreditsText;
    private Spinner spinnertext;

    private DatabaseReference databaseReference;

    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            identifier = extras.getString("identifier");
        }

        instantiateSpinner();
        instantiateButtons();
    }

    private void instantiateSpinner() {
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(0);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) +"frequency", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void instantiateButtons() {
        saveNewToDoButton = (Button) findViewById(R.id.saveNewToDoButton);
        saveNewToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTextFieldsToDatabase();

                setAlarm( v);

                finish();
            }
        });

        cancelNewToDoButton = (Button) findViewById(R.id.cancelNewToDoButton);
        cancelNewToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveTextFieldsToDatabase() {
        nameText = (EditText) findViewById(R.id.name);
        dueDateText = (EditText) findViewById(R.id.duedate);
        timeNeededText = (EditText) findViewById(R.id.timeneeded);
        weightText = (EditText) findViewById(R.id.weight);
        courseCreditsText = (EditText) findViewById(R.id.coursecredits);
        spinnertext = (Spinner) findViewById(R.id.spinner);

        TextFieldsInformation info = new TextFieldsInformation(nameText.getText().toString(),
                dueDateText.getText().toString(),
                timeNeededText.getText().toString(),
                weightText.getText().toString(),
                courseCreditsText.getText().toString(),
                spinnertext.getSelectedItem().toString());

        databaseReference.child(identifier).push().setValue(info);

    }


    public void setAlarm(View v) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>(3);

       String alarmDate = ((EditText) findViewById(R.id.duedate)).getText().toString();

        String[] id = identifier.split("-");
        long dayid = Long.parseLong(id[0]);
        long monthid = Long.parseLong(id[1]);
        long yearid = Long.parseLong(id[2]);

        Long idend = dayid*24*60*60*1000+ monthid*30*60*60*1000+ yearid*365*60*60*1000;


        String[] parts = alarmDate.split("-");

        long day = Long.parseLong(parts[0]);
        long month = Long.parseLong(parts[1]);
        long year = Long.parseLong(parts[2]);

        Long date = day*24*60*60*1000+ month*30*60*60*1000+ year*365*60*60*1000;

        int i = 0;

        for (i = 0; i <intentArray.size(); i++){
            if (intentArray.get(i) == null)
                break;
        }


        Long currentTime = new GregorianCalendar().getTimeInMillis();

        if ((idend -date) < currentTime)
            return;

        Long alertTime = new GregorianCalendar().getTimeInMillis() + (idend-date);


        Intent alertIntent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, pendingIntent);

        intentArray.add(pendingIntent);



    }

}