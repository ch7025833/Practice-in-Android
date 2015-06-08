package com.example.chenhao.myscheduleapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartActivity extends ActionBarActivity {

    private Button newEvent;
    private Button showEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        newEvent = (Button) findViewById(R.id.new_event_button);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the new activity to the calendar
                Intent intent = new Intent(StartActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });

        showEvent = (Button) findViewById(R.id.show_event_button);
        showEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the new activity to the list of events.
            }
        });

    }

}
