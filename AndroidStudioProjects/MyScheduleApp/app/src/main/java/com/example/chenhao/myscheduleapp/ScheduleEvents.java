package com.example.chenhao.myscheduleapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by chenhao on 5/2/2015.
 */
public class ScheduleEvents {

    private static ScheduleEvents scheduleEvents;
    private Context appContext;
    private LinkedList<Event> events;

    private ScheduleEvents(Context appContext){
        this.appContext = appContext;
        events = new LinkedList<Event>();
    }

    public static ScheduleEvents getEvents(Context context){
        if (scheduleEvents == null){
            return scheduleEvents = new ScheduleEvents(context.getApplicationContext());
        }else {
            return scheduleEvents;
        }
    }

    public LinkedList<Event> getEvents() {
        return events;
    }

    public Event getEvent(UUID id){
        for (Event event : events){
            if (event.getId().equals(id)){
                return event;
            }
        }
        return null;
    }

    public void addEvent(Event event){
        if (events != null){
            events.add(event);
        }
    }
}
