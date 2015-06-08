package com.example.chenhao.myscheduleapp;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chenhao on 5/2/2015.
 */
public class Event {

    private UUID id;
    private String context;
    private Date date;

    public Event(String context){
        this.context = context;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
