package com.example.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ToDoAdd extends AppCompatActivity {
    private Spinner spinner;
    private Button saveNewToDoButton;
    private Button cancelNewToDoButton;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);
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
                saveToXML(v);
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

    private void saveToXML(View v) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Root element
            Element rootElement = doc.createElement("ToDoList");
            doc.appendChild(rootElement);

            // Current date selected (element, attribute, value)
            Element dateElement = doc.createElement("date");
            Attr fullDateAttr = doc.createAttribute("fullDate");
            fullDateAttr.setValue("21-01-2017");
            rootElement.appendChild(dateElement);

            // Date specific elements
            Element nameElement = doc.createElement("nameElement");
            Attr nameAttr = doc.createAttribute("nameAttr");
            nameAttr.setValue("Assignment");
            nameElement.setAttributeNode(nameAttr);
            nameElement.appendChild(doc.createTextNode("Assignment"));
            dateElement.appendChild(nameElement);

            Element dueDateElement = doc.createElement("dueDateElement");
            Attr dueDateAttr = doc.createAttribute("dueDateAttr");
            dueDateAttr.setValue("25-01-2017");
            dueDateElement.setAttributeNode(dueDateAttr);
            dueDateElement.appendChild(doc.createTextNode("25-01-2017"));
            dateElement.appendChild(dueDateElement);

            Element timeNeededElement = doc.createElement("timeNeededElement");
            Attr timeNeededAttr = doc.createAttribute("timeNeededAttr");
            timeNeededAttr.setValue("2 hours");
            timeNeededElement.setAttributeNode(timeNeededAttr);
            timeNeededElement.appendChild(doc.createTextNode("2 hours"));
            dateElement.appendChild(timeNeededElement);

            Element weightElement = doc.createElement("weightElement");
            Attr weightAttr = doc.createAttribute("weightAttr");
            weightAttr.setValue("10%");
            weightElement.setAttributeNode(weightAttr);
            weightElement.appendChild(doc.createTextNode("10%"));
            dateElement.appendChild(weightElement);

            Element creditsElement = doc.createElement("creditsElement");
            Attr creditAttr = doc.createAttribute("creditAttr");
            creditAttr.setValue("3");
            creditsElement.setAttributeNode(creditAttr);
            creditsElement.appendChild(doc.createTextNode("3"));
            dateElement.appendChild(creditsElement);

            Element frequencyElement = doc.createElement("frequencyElement");
            Attr frequencyAttr = doc.createAttribute("frequencyAttr");
            frequencyAttr.setValue("Daily");
            frequencyElement.setAttributeNode(frequencyAttr);
            frequencyElement.appendChild(doc.createTextNode("Daily"));
            dateElement.appendChild(frequencyElement);

            // Write to XML file
            File dir = getApplicationContext().getFilesDir();
            File file = new File(dir, "ToDoListData.xml");

            FileOutputStream outputStream;

            //if (!root.exists()) {
            //   root.mkdirs();
            //}

            // File filePath = new File(root, "ToDoListData.xml");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}