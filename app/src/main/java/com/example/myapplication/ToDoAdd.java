package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);

        databaseReference = FirebaseDatabase.getInstance().getReference();

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

        TextFieldsInformation info = new TextFieldsInformation(nameText.getText().toString(),
                dueDateText.getText().toString(),
                timeNeededText.getText().toString(),
                weightText.getText().toString(),
                courseCreditsText.getText().toString());

        databaseReference.child("Placeholder date").setValue(info);
    }
}