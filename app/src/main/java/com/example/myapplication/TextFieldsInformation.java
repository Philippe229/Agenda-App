package com.example.myapplication;

/**
 * Created by Philippe on 21/01/2017.
 */

public class TextFieldsInformation {
    public String name;
    public String dueDate;
    public String timeNeeded;
    public String weight;
    public String courseCredits;

    public TextFieldsInformation(String nameArg, String dueDateArg, String timeNeededArg, String weightArg, String courseCreditsArg) {
        name = nameArg;
        dueDate = dueDateArg;
        timeNeeded = timeNeededArg;
        weight = weightArg;
        courseCredits = courseCreditsArg;
    }
}
