
package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToDoList extends AppCompatActivity {
    private ImageButton addToDo;
    private ImageButton refresh;
    private List<String> toDoEntries;
    private ListView toDoListEntries;
    private DatabaseReference databaseReference;

    private int dayOfMonth;
    private int month;
    private int year;

    private String identifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        // Current date
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dayOfMonth = extras.getInt("dayOfMonth");
            month = extras.getInt("month");
            year = extras.getInt("year");
        }

        identifier = dayOfMonth + "-" + month + "-" + year;

        databaseReference = FirebaseDatabase.getInstance().getReference().child(identifier);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toDoEntries = collectNames((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (toDoEntries == null) {
            toDoEntries = new ArrayList<>();
            toDoEntries.add("No tasks");
        }

        toDoListEntries = (ListView) findViewById(R.id.toDoListEntries);

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoEntries);

        toDoListEntries.setAdapter(stringAdapter);

        addToDo = (ImageButton) findViewById(R.id.addToDoEntry);
        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ToDoAdd.class);
                intent.putExtra("identifier", identifier);
                startActivity(intent);
            }
        });

        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshToDoEntries();
            }
        });
    }

    private void refreshToDoEntries() {

        if (toDoEntries == null) {
            toDoEntries = new ArrayList<>();
            toDoEntries.add("No tasks");
        }

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoEntries);

        toDoListEntries.setAdapter(stringAdapter);
    }

    private List collectNames(Map<String, Object> identifier) {
        if (identifier == null || identifier.entrySet() == null) {
            return null;
        }

        ArrayList<String> names = new ArrayList<>();

        for (Map.Entry<String, Object> entry : identifier.entrySet()){

            // Get To Do parameters
            Map singleToDo = (Map) entry.getValue();

            // Get name field
            names.add((String) singleToDo.get("name"));
        }

        return names;
    }
}
