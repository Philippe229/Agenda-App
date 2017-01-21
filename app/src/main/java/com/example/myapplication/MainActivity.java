package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.NotificationManager;
import android.app.PendingIntent;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {


//------------------Reminder---------------------------
    NotificationCompat.Builder notification ;
    private static final int uniqueID = 000000;
//-----------------------------------------------------



    CalendarView calendar;
    Button toDoListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view,int year, int month, int dayOfMonth){
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        toDoListButton = (Button) findViewById(R.id.toDoListButton);
        toDoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ToDoList.class);
                startActivity(intent);
            }
        });


        //------------------Reminders--------------------------------------
        notification =   new NotificationCompat.Builder(this);



        notification.setAutoCancel(true);
        //--------------------------------------------------------




    }








    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }



    public void fireNotification(View view){
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentTitle("My notification");
        notification.setContentText("Hello World!");

        //for displaying the time associated with a notification
        notification.setWhen(System.currentTimeMillis());

// To trigger notifications at certain times use AlarmManager

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);


        //  notification.setContentIntent(pendingIntent);
        // The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notification.setContentIntent(resultPendingIntent);




        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // ID to update notification later
        nm.notify(uniqueID, notification.build());

        //--------------------------------------------------------

    }










}
