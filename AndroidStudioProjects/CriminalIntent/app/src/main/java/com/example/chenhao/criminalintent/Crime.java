package com.example.chenhao.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by chenhao on 4/2/2015.
 */
public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";

    private UUID mId;
    private String mTitle;
    private Context mFragmentContext;

    private Date mDate;
    private Photo mPhoto;
    private boolean mSolved;
    private String mSuspect;

    //constructor for load crimes from the json file
    public Crime(JSONObject jsonObject) throws JSONException{

        mId = UUID.fromString(jsonObject.getString(JSON_ID));
        mTitle = jsonObject.getString(JSON_TITLE);
        mSolved = jsonObject.getBoolean(JSON_SOLVED);
        mDate = new Date(jsonObject.getLong(JSON_DATE));
        if (jsonObject.has(JSON_PHOTO)){
            mPhoto = new Photo(jsonObject.getJSONObject(JSON_PHOTO));
        }
        if (jsonObject.has(JSON_SUSPECT)){
            mSuspect = jsonObject.getString(JSON_SUSPECT);
        }
    }

    //context is for specific device context.
    public Crime(Context fragmentContext){
        //generate unique identifier.
        mId = UUID.randomUUID();
        mDate = new Date();
        mFragmentContext = fragmentContext;
    }

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }


    public JSONObject toJSON() throws JSONException{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID,mId.toString());
        jsonObject.put(JSON_TITLE,mTitle);
        jsonObject.put(JSON_DATE,mDate.getTime());
        jsonObject.put(JSON_SOLVED,mSolved);
        if (mPhoto != null){
            jsonObject.put(JSON_PHOTO,mPhoto.toJSON());
            jsonObject.put(JSON_SUSPECT,mSuspect);
        }
        return jsonObject;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {

        //different format of the Date.
        //java.text.DateFormat format;
        //format = DateFormat.getLongDateFormat(mFragmentContext);
        //format = java.text.DateFormat.getDateTimeInstance();
        //return format.format(mDate);
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        mTitle = Title;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public Photo getPhoto(){
        return mPhoto;
    }

    public void setPhoto(Photo photo){
        mPhoto = photo;
    }

    public String getSuspect(){
        return mSuspect;
    }

    public void setSuspect(String suspect){
        mSuspect = suspect;
    }
}
