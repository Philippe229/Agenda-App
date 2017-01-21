
package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ToDoList extends AppCompatActivity {
    private ImageButton addToDo;
    private List<String> toDoEntries;
    private ListView toDoListEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        generateToDoList();

        addToDo = (ImageButton) findViewById(R.id.addToDoEntry);
        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ToDoAdd.class);
                startActivity(intent);
            }
        });
    }

    private void generateToDoList() {
        toDoListEntries = (ListView) findViewById(R.id.toDoListEntries);

        toDoEntries = new ArrayList<>();
        toDoEntries.add("Assignment due tomorrow");
        toDoEntries.add("Quiz next Friday");

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoEntries);

        toDoListEntries.setAdapter(stringAdapter);
    }
}
